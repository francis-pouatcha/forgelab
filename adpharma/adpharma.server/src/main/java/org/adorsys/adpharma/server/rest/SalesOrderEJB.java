package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DirectSalesClosedEvent;
import org.adorsys.adpharma.server.events.DocumentCanceledEvent;
import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.ReturnSalesEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.repo.SalesOrderRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;

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

	public SalesOrder create(SalesOrder entity)
	{
		Login user = securityUtilEJB.getConnectedUser();
		entity.setAgency(user.getAgency());
		entity.setSalesAgent(user);

		if(entity.getCustomer()==null || entity.getCustomer().getId()==null){
			Customer otherCustomers = customerEJB.otherCustomers();
			entity.setCustomer(otherCustomers);
		}
		return repository.save(attach(entity));
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

	public List<SalesOrder> findBy(SalesOrder entity, int start, int max, SingularAttribute<SalesOrder, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(SalesOrder entity, SingularAttribute<SalesOrder, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<SalesOrder> findByLike(SalesOrder entity, int start, int max, SingularAttribute<SalesOrder, ?>[] attributes)
	{
		SalesOrder salesOrder = attach(entity);
		return repository.findByLike(salesOrder, start, max, attributes);
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

		// composed collections
		Set<SalesOrderItem> salesOrderItems = entity.getSalesOrderItems();
		for (SalesOrderItem salesOrderItem : salesOrderItems)
		{
			salesOrderItem.setSalesOrder(entity);
		}

		return entity;
	}

	public SalesOrder processReturn(SalesOrder salesOrder){
		salesOrder.setAlreadyReturned(Boolean.TRUE);
		salesOrder.calculateTotalReturnAmount();
		SalesOrder update = update(salesOrder);
		returnSalesEvent.fire(update);
		return update;
	}
	public SalesOrder saveAndClose(SalesOrder salesOrder) {
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		BigDecimal amountAfterTax = BigDecimal.ZERO;
		BigDecimal amountBeforeTax = BigDecimal.ZERO;
		BigDecimal amountVAT = BigDecimal.ZERO;
		BigDecimal amountDiscount = BigDecimal.ZERO;
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();
		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			salesOrderItem.setRecordDate(new Date());
			salesOrderItem.calucateDeliveryQty();
			salesOrderItem.calculateAmount();
			salesOrderItem = salesOrderItemEJB.update(salesOrderItem);
			
//			String internalPic = salesOrderItem.getInternalPic();
//			
//			Article article = salesOrderItem.getArticle();
//			BigDecimal sppu = article.getSppu();
//			amountAfterTax = amountAfterTax.add(salesOrderItem.getTotalSalePrice());
//			
//			// compute further sales order data.
//			amountBeforeTax = amountBeforeTax.add(salesOrderItem.getTotalSalePrice());
//			salesOrderItem.get
		}
		
		salesOrder.setAmountAfterTax(amountAfterTax);
		salesOrder.setAmountBeforeTax(amountBeforeTax);
		salesOrder.setAmountVAT(amountVAT);
		salesOrder.setAmountDiscount(amountDiscount);
		salesOrder.setCreationDate(creationDate);
		salesOrder.setSalesAgent(creatingUser);
		salesOrder.setSalesOrderStatus(DocumentProcessingState.CLOSED);
		SalesOrder closedSales = update(salesOrder);
		salesOrderClosedEvent.fire(closedSales);
		return closedSales;
	}

	public SalesOrder cancelSalesOrder(SalesOrder salesOrder) {
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
}
