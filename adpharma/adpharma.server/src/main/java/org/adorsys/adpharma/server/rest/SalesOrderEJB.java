package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
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
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.SalesOrderItem_;
import org.adorsys.adpharma.server.jpa.VAT;
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
		entity.setCreationDate(new Date());

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
		SalesOrder salesOrder = attach(entity);
		return repository.findBy(salesOrder, start, max, attributes);
	}

	public Long countBy(SalesOrder entity, SingularAttribute<SalesOrder, ?>[] attributes)
	{
		SalesOrder salesOrder = attach(entity);
		return repository.count(salesOrder, attributes);
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

		// composed collections. No composition
//		Set<SalesOrderItem> salesOrderItems = entity.getSalesOrderItems();
//		for (SalesOrderItem salesOrderItem : salesOrderItems)
//		{
//			salesOrderItem.setSalesOrder(entity);
//		}

		return entity;
	}

	public SalesOrder processReturn(SalesOrder salesOrder){
		salesOrder.setAlreadyReturned(Boolean.TRUE);
		salesOrder.calculateTotalReturnAmount();
		SalesOrder update = update(salesOrder);
		returnSalesEvent.fire(update);
		return update;
	}
	private static final BigDecimal HUNDRED = new BigDecimal(100);
	public SalesOrder saveAndClose(SalesOrder salesOrder) {
		SalesOrder original = findById(salesOrder.getId());
		salesOrder = attach(salesOrder);
		salesOrder.setAmountAfterTax(original.getAmountAfterTax());
		salesOrder.setAmountBeforeTax(original.getAmountBeforeTax());
		salesOrder.setAmountVAT(original.getAmountVAT());
		if(salesOrder.getAmountDiscount()==null)
			salesOrder.setAmountDiscount(BigDecimal.ZERO);

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
	
	public void handleSalesOrderItemCreatedEvent(@Observes @DocumentCreatedEvent SalesOrderItem salesOrderItem){
		updateSalesOrder(salesOrderItem, false);
	}
	public void handleSalesOrderItemProcessedEvent(@Observes @DocumentProcessedEvent SalesOrderItem salesOrderItem){
		updateSalesOrder(salesOrderItem, false);
	}
	public void handleSalesOrderItemDeletedEvent(@Observes @DocumentDeletedEvent SalesOrderItem salesOrderItem){
		updateSalesOrder(salesOrderItem, true);
	}
	
	
	@SuppressWarnings("unchecked")
	private void updateSalesOrder(SalesOrderItem updatingSalesOrderItem, boolean deleted) {
		
		SalesOrderItem searchInput = new SalesOrderItem();
		SalesOrder salesOrder = updatingSalesOrderItem.getSalesOrder();
		salesOrder = findById(salesOrder.getId());
		if(DocumentProcessingState.CLOSED.equals(salesOrder.getSalesOrderStatus()))
			throw new IllegalStateException("Sales order closed.");
		searchInput.setSalesOrder(salesOrder);
		
		@SuppressWarnings("rawtypes")
		SingularAttribute[] attributes = new SingularAttribute[]{SalesOrderItem_.salesOrder};
		Long count = salesOrderItemEJB.countBy(searchInput, attributes );
		int start = 0;
		int max = 100;
		BigDecimal amountAfterTax = BigDecimal.ZERO;
		BigDecimal amountBeforeTax = BigDecimal.ZERO;
		BigDecimal amountVAT = BigDecimal.ZERO;
		while(start<=count){
			List<SalesOrderItem> found = salesOrderItemEJB.findBy(searchInput, start , max, attributes);
			start +=max;
			
			for (SalesOrderItem salesOrderItem : found) {
				
				// use the current event object. Not sure about transactional behavior here.
				if(updatingSalesOrderItem.getId().equals(salesOrderItem.getId())){
					if(deleted) continue;// do not account for the deleted item. In case it appears here.
					salesOrderItem = updatingSalesOrderItem;
				}
				
				amountAfterTax=amountAfterTax.add(salesOrderItem.getTotalSalePrice());
				VAT vat = salesOrderItem.getVat();
				if(vat!=null){
					amountVAT = amountVAT.add(salesOrderItem.getTotalSalePrice().multiply(vat.getRate()).divide(HUNDRED));
				}
				amountBeforeTax = amountAfterTax.subtract(amountVAT);
			}
			
		}
		salesOrder.setAmountAfterTax(amountAfterTax);
		salesOrder.setAmountBeforeTax(amountBeforeTax);
		salesOrder.setAmountVAT(amountVAT);
		salesOrder = update(salesOrder);
		updatingSalesOrderItem.setSalesOrder(salesOrder);
	}
	
}
