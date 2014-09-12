package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DirectSalesClosedEvent;
import org.adorsys.adpharma.server.events.DocumentCanceledEvent;
import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentCreatedEvent;
import org.adorsys.adpharma.server.events.DocumentDeletedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.events.ReturnSalesEvent;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Login_;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderAdvenceSearchData;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.SalesOrderItem_;
import org.adorsys.adpharma.server.jpa.SalesOrder_;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.repo.SalesOrderRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.CurencyUtil;
import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class SalesOrderEJB
{
	

	@Inject
	private SalesOrderRepository repository;

	@Inject
	private CustomerMerger customerMerger;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private CashDrawerMerger cashDrawerMerger;

	@Inject
	private VATMerger vATMerger;

	@Inject
	private LoginEJB loginEJB ;

	@Inject
	private InsurranceMerger insurranceMerger;

	@Inject
	private SalesOrderItemMerger salesOrderItemMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@EJB
	private SecurityUtil securityUtil;

	@EJB
	private SalesOrderItemEJB salesOrderItemEJB;

	@Inject
	@DocumentClosedEvent
	private Event<SalesOrder> salesOrderClosedEvent;

	@Inject
	@DocumentCanceledEvent
	private Event<SalesOrder> salesOrderCanceledEvent;

	@Inject
	@DirectSalesClosedEvent
	private Event<SalesOrder> directSalesClosedEvent;

	@Inject
	@ReturnSalesEvent
	private Event<SalesOrder> returnSalesEvent;

	@EJB
	private CustomerEJB customerEJB;

	@Inject
	private SecurityUtil securityUtilEJB;

	@Inject
	private EntityManager em ;

	public SalesOrder create(SalesOrder entity)
	{
		Login user = securityUtilEJB.getConnectedUser();
		entity.setAgency(user.getAgency());
		entity.setSalesAgent(user);
		entity.setCreationDate(new Date());

		if(entity.getCustomer()==null || entity.getCustomer().getId()==null){
			Customer otherCustomers = customerEJB.otherCustomers();
			entity.setCustomer(otherCustomers);
		}
		//		entity.setSoNumber(StringUtils.upperCase(SequenceGenerator.getSequence(SequenceGenerator.SALE_SEQUENCE_PREFIXE)));
		//		return repository.save(attach(entity)); 
		SalesOrder save = repository.save(attach(entity)); 
		save.setSoNumber(SequenceGenerator.SALE_SEQUENCE_PREFIXE+save.getId());
		return repository.save(save);
	}

	public SalesOrder changeCustomer(Long salesId, Customer customer){

		SalesOrder salesOrder = findById(salesId);
		Customer create = customerEJB.create(customer);
		salesOrder.setCustomer(customer);
		return update(salesOrder);
	}

	public SalesOrder deleteById(Long id)
	{
		SalesOrder entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public List<SalesOrder> advenceSearch(SalesOrderAdvenceSearchData data){
		List<SalesOrder> sales = new ArrayList<SalesOrder>();
		String query ="SELECT DISTINCT(s.salesOrder) FROM SalesOrderItem AS s WHERE s.id != NULL  ";
		if(data.getFromDate()!=null)
			query = query+" AND s.salesOrder.creationDate >= :fromDate ";

		if(data.getToDate()!=null)
			query = query+" AND s.salesOrder.creationDate <= :toDate ";

		if(data.getSaller()!=null)
			query = query+" AND s.salesOrder.salesAgent = :salesAgent ";

		if(data.getSate()!=null)
			query = query+" AND s.salesOrder.salesOrderStatus = :salesOrderStatus ";

		if(StringUtils.isNotBlank(data.getArticleName()))
			query = query+" AND LOWER(s.article.articleName) LIKE LOWER(:articleName)";

		Query querys = em.createQuery(query) ;

		if(data.getFromDate()!=null)
			querys.setParameter("fromDate", data.getFromDate());

		if(data.getToDate()!=null)
			querys.setParameter("toDate", data.getToDate());

		if(data.getSaller()!=null)
			querys.setParameter("salesAgent", data.getSaller());

		if(data.getSate()!=null)
			querys.setParameter("salesOrderStatus", data.getSate());

		if(StringUtils.isNotBlank(data.getArticleName())){
			String articleName = data.getArticleName()+"%";
			querys.setParameter("articleName", articleName);

		}
		sales = (List<SalesOrder>) querys.getResultList();

		return sales;
	}

	public SalesOrder update(SalesOrder entity)
	{
		return repository.save(attach(entity));
	}

	public SalesOrder findById(Long id)						
	{
		return repository.findBy(id);
	}

	public List<SalesOrder> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<SalesOrder> findBy(SalesOrder entity, int start, int max, SingularAttribute<SalesOrder, Object>[] attributes)
	{
		SalesOrder salesOrder = attach(entity);
		return repository.criteriafindBy(salesOrder, attributes).orderDesc(SalesOrder_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countBy(SalesOrder entity, SingularAttribute<SalesOrder, ?>[] attributes)
	{
		SalesOrder salesOrder = attach(entity);
		return repository.count(salesOrder, attributes);
	}

	public List<SalesOrder> findByLike(SalesOrder entity, int start, int max, SingularAttribute<SalesOrder, Object>[] attributes)
	{
		SalesOrder salesOrder = attach(entity);
		return repository.criteriafindBy(salesOrder, attributes).orderDesc(SalesOrder_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countByLike(SalesOrder entity, SingularAttribute<SalesOrder, ?>[] attributes)
	{
		SalesOrder salesOrder = attach(entity);
		return repository.countLike(salesOrder, attributes);
	}

	private SalesOrder attach(SalesOrder entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCashDrawer(cashDrawerMerger.bindAggregated(entity.getCashDrawer()));

		// aggregated
		entity.setCustomer(customerMerger.bindAggregated(entity.getCustomer()));

		// aggregated
		entity.setInsurance(insurranceMerger.bindAggregated(entity.getInsurance()));

		// aggregated
		entity.setVat(vATMerger.bindAggregated(entity.getVat()));

		// aggregated
		entity.setSalesAgent(loginMerger.bindAggregated(entity.getSalesAgent()));

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// composed collections. No composition
		//		Set<SalesOrderItem> salesOrderItems = entity.getSalesOrderItems();
		//		for (SalesOrderItem salesOrderItem : salesOrderItems)
		//		{
		//			salesOrderItem.setSalesOrder(entity);
		//		}

		return entity;
	}

	public SalesOrder processReturn(SalesOrder salesOrder){
		SalesOrder original = findById(salesOrder.getId());
		salesOrder = attach(salesOrder);
		salesOrder.setVersion(original.getVersion());
		salesOrder.setInsurance(original.getInsurance());
		salesOrder.setAlreadyReturned(Boolean.TRUE);
		salesOrder.calculateTotalReturnAmount();
		SalesOrder update = update(salesOrder);
		returnSalesEvent.fire(update);
		return update;
	}

	public SalesOrder saveAndClose(SalesOrder salesOrder) {
		BigDecimal amountDiscount = salesOrder.getAmountDiscount();
		BigDecimal discountRate = salesOrder.getDiscountRate();
		if(DocumentProcessingState.CLOSED.equals(salesOrder.getSalesOrderStatus()))
			return  findById(salesOrder.getId()) ;
		Login realSaller = getRealSaller(salesOrder.getSalesKey());
		if(realSaller==null) throw new IllegalStateException("Saller is required !") ;
		//		SalesOrder original = findById(salesOrder.getId());
		salesOrder = attach(salesOrder);
		Insurrance insurance = salesOrder.getInsurance();
		if(insurance!=null&& salesOrder.getCustomer().equals(insurance.getInsurer())){
			Customer customer = insurance.getCustomer();
			salesOrder.setCustomer(customer);
		}
		//		salesOrder.setAmountAfterTax(original.getAmountAfterTax());
		//		salesOrder.setAmountBeforeTax(original.getAmountBeforeTax());
		//		salesOrder.setAmountVAT(original.getAmountVAT());
		//		if(salesOrder.getAmountDiscount()==null)
		//			salesOrder.setAmountDiscount(BigDecimal.ZERO);

		salesOrder.setSalesAgent(realSaller);
		salesOrder = repository.save(salesOrder);
		rebuildSaleOrder(salesOrder);
		salesOrder.setSalesOrderStatus(DocumentProcessingState.CLOSED);
		SalesOrder closedSales = update(salesOrder);
		if(discountRate==null||BigDecimal.ZERO.compareTo(discountRate)==0){
			if(amountDiscount!=null && BigDecimal.ZERO.compareTo(amountDiscount)!=0){
				salesOrder.setAmountDiscount(amountDiscount);
				salesOrder.setAmountAfterTax(salesOrder.getAmountAfterTax().subtract(amountDiscount));
				salesOrder = repository.save(salesOrder);
			}
		}
		salesOrderClosedEvent.fire(closedSales);
		return closedSales;
	}

	public SalesOrder cancelSalesOrder(SalesOrder salesOrder) {
		salesOrder = findById(salesOrder.getId());
		if(Boolean.TRUE.equals(salesOrder.getCashed())){
			throw new IllegalStateException("Can not cancel frozen sales order.");
		}
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		salesOrder.setSalesOrderStatus(DocumentProcessingState.SUSPENDED);
		salesOrderCanceledEvent.fire(salesOrder);
		salesOrder.setCreationDate(creationDate);
		salesOrder.setSalesAgent(creatingUser);
		SalesOrder canceledSales = update(salesOrder);
		return canceledSales;
	}

	/**
	 * Freeze the order of this customer with setCashed = true. Sales order can no
	 * be canceled anymore.
	 * 
	 * @param payment
	 */
	public void handleCustomerInvoiceProcessed(@Observes @DirectSalesClosedEvent CustomerInvoice customerInvoice){
		SalesOrder salesOrder = customerInvoice.getSalesOrder();
		salesOrder.setCashed(Boolean.TRUE);// Freezes sales order
		salesOrder = update(salesOrder);
		directSalesClosedEvent.fire(salesOrder);
	}

	public void handleSalesOrderItemCreatedEvent(@Observes @DocumentCreatedEvent SalesOrderItem salesOrderItem){
		updateSalesOrder(salesOrderItem, false);
	}
	public void handleSalesOrderItemProcessedEvent(@Observes @DocumentProcessedEvent SalesOrderItem salesOrderItem){
		updateSalesOrder(salesOrderItem, false);
	}
	public void handleSalesOrderItemDeletedEvent(@Observes @DocumentDeletedEvent SalesOrderItem salesOrderItem){
		updateSalesOrder(salesOrderItem, true);
	}


	private void updateSalesOrder(SalesOrderItem updatingSalesOrderItem, boolean deleted) {

		SalesOrder salesOrder = updatingSalesOrderItem.getSalesOrder();
		salesOrder = findById(salesOrder.getId());
		if(DocumentProcessingState.CLOSED.equals(salesOrder.getSalesOrderStatus()))
			throw new IllegalStateException("Sales order closed.");

		VAT vat = updatingSalesOrderItem.getVat();
		BigDecimal vatRate = vat==null?BigDecimal.ZERO:VAT.getRawRate(vat.getRate());
		BigDecimal totalSalePrice = updatingSalesOrderItem.getTotalSalePrice();
		BigDecimal amountBeforeTax = CurencyUtil.round(totalSalePrice.divide(BigDecimal.ONE.add(vatRate), 2, RoundingMode.HALF_EVEN));
		BigDecimal discountRateAbs = salesOrder.getDiscountRate();
		if(discountRateAbs!=null && discountRateAbs.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal discaountRate = VAT.getRawRate(discountRateAbs);
			BigDecimal discount = CurencyUtil.round(amountBeforeTax.multiply(discaountRate));
			BigDecimal salesOrderDiscount = salesOrder.getAmountDiscount()==null?BigDecimal.ZERO:salesOrder.getAmountDiscount();

			if(deleted){
				salesOrder.setAmountDiscount(salesOrderDiscount.subtract(discount));
			} else {
				salesOrder.setAmountDiscount(salesOrderDiscount.add(discount));
			}
			amountBeforeTax = amountBeforeTax.subtract(discount);
		}
		BigDecimal salesOrderAmountBeforeTax = salesOrder.getAmountBeforeTax()==null?BigDecimal.ZERO:salesOrder.getAmountBeforeTax();
		BigDecimal salesOrderTaxAmount = salesOrder.getAmountVAT()==null?BigDecimal.ZERO:salesOrder.getAmountVAT();
		BigDecimal amountVAT = CurencyUtil.round(amountBeforeTax.multiply(vatRate));
		BigDecimal amountAfterTax = CurencyUtil.round(amountBeforeTax.add(amountVAT));
		BigDecimal salesOrderAmountAfterTax = salesOrder.getAmountAfterTax()==null?BigDecimal.ZERO:salesOrder.getAmountAfterTax();

		if(deleted){
			salesOrder.setAmountBeforeTax(salesOrderAmountBeforeTax.subtract(amountBeforeTax));
			salesOrder.setAmountVAT(salesOrderTaxAmount.subtract(amountVAT));
			salesOrder.setAmountAfterTax(salesOrderAmountAfterTax.subtract(amountAfterTax));
		} else {
			salesOrder.setAmountBeforeTax(salesOrderAmountBeforeTax.add(amountBeforeTax));
			salesOrder.setAmountVAT(salesOrderTaxAmount.add(amountVAT));
			salesOrder.setAmountAfterTax(salesOrderAmountAfterTax.add(amountAfterTax));
		}
		rebuildSaleOrder(salesOrder);

		salesOrder = update(salesOrder);
		updatingSalesOrderItem.setSalesOrder(salesOrder);
	}

	private void rebuildSaleOrder(SalesOrder salesOrder) {
		if(DocumentProcessingState.CLOSED.equals(salesOrder.getSalesOrderStatus()))
			throw new IllegalStateException("Sales order closed.");

		SalesOrderItem searchInput = new SalesOrderItem();
		searchInput.setSalesOrder(salesOrder);
		@SuppressWarnings("rawtypes")
		SingularAttribute[] attributes = new SingularAttribute[]{SalesOrderItem_.salesOrder};
		Long count = salesOrderItemEJB.countBy(searchInput, attributes );
		int start = 0;
		int max = 100;

		BigDecimal salesOrderAmountBeforeTax = BigDecimal.ZERO;
		BigDecimal salesOrderTaxAmount = BigDecimal.ZERO;
		BigDecimal salesOrderDiscount = BigDecimal.ZERO;
		BigDecimal salesOrderAmountAfterTax = BigDecimal.ZERO;
		BigDecimal discountRateAbs = salesOrder.getDiscountRate();
		BigDecimal amountDiscount = salesOrder.getAmountDiscount();
		BigDecimal discaountRate = VAT.getRawRate(discountRateAbs);

		while(start<=count){
			List<SalesOrderItem> found = salesOrderItemEJB.findBy(searchInput, start , max, attributes);
			start +=max;

			for (SalesOrderItem salesOrderItem : found) {
				VAT vat = salesOrderItem.getVat();
				BigDecimal vatRate = vat==null?BigDecimal.ZERO:VAT.getRawRate(vat.getRate());
				BigDecimal totalSalePrice = salesOrderItem.getTotalSalePrice();
				BigDecimal amountBeforeTax = totalSalePrice.divide(BigDecimal.ONE.add(vatRate), 8, RoundingMode.HALF_EVEN);
				if(discountRateAbs!=null && discountRateAbs.compareTo(BigDecimal.ZERO)!=0){
					BigDecimal discount = amountBeforeTax.multiply(discaountRate);
					salesOrderDiscount = salesOrderDiscount.add(discount);					
					amountBeforeTax = amountBeforeTax.subtract(discount);
				}
				BigDecimal amountVAT = amountBeforeTax.multiply(vatRate);
				BigDecimal amountAfterTax = amountBeforeTax.add(amountVAT);

				salesOrderAmountBeforeTax = salesOrderAmountBeforeTax.add(amountBeforeTax);
				salesOrderTaxAmount = salesOrderTaxAmount.add(amountVAT);
				salesOrderAmountAfterTax = salesOrderAmountAfterTax.add(amountAfterTax);
			}
		}

		salesOrder.setAmountAfterTax(salesOrderAmountAfterTax);
		salesOrder.setAmountBeforeTax(salesOrderAmountBeforeTax);
		salesOrder.setAmountVAT(salesOrderTaxAmount);
		salesOrder.setAmountDiscount(salesOrderDiscount);
	}

	@SuppressWarnings("unchecked")
	private Login getRealSaller(String saleKey){
		Login login = new Login();
		login.setSaleKey(saleKey);
		List<Login> findBy = loginEJB.findBy(login, 0, 1, new SingularAttribute[]{Login_.saleKey});
		if(!findBy.isEmpty())
			return findBy.iterator().next();

		return null;
	}

}
