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
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.CustomerInvoice_;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.jpa.InvoiceType;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.repo.CustomerInvoiceRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
public class CustomerInvoiceEJB {

	@Inject
	private CustomerInvoiceRepository repository;

	@Inject
	private CustomerMerger customerMerger;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private CustomerInvoiceItemMerger customerInvoiceItemMerger;

	@Inject
	private SalesOrderMerger salesOrderMerger;

	@Inject
	private InsurranceMerger insurranceMerger;

	@Inject
	private PaymentCustomerInvoiceAssocMerger paymentCustomerInvoiceAssocMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private CustomerInvoiceItemEJB customerInvoiceItemEJB;

	@EJB
	private PaymentCustomerInvoiceAssocEJB paymentCustomerInvoiceAssocEJB;
	
	@Inject
	@DocumentProcessedEvent
	private Event<CustomerInvoice> customerInvoiceProcessedEvent;
	
	public CustomerInvoice create(CustomerInvoice entity) {
		return repository.save(attach(entity));
	}

	public CustomerInvoice deleteById(Long id) {
		CustomerInvoice entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public CustomerInvoice update(CustomerInvoice entity) {
		return repository.save(attach(entity));
	}

	public CustomerInvoice findById(Long id) {
		return repository.findBy(id);
	}

	public List<CustomerInvoice> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<CustomerInvoice> findBy(CustomerInvoice entity, int start,
			int max, SingularAttribute<CustomerInvoice, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CustomerInvoice entity,
			SingularAttribute<CustomerInvoice, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CustomerInvoice> findByLike(CustomerInvoice entity, int start,
			int max, SingularAttribute<CustomerInvoice, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CustomerInvoice entity,
			SingularAttribute<CustomerInvoice, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CustomerInvoice attach(CustomerInvoice entity) {
		if (entity == null)
			return null;

		// aggregated
		entity.setCustomer(customerMerger.bindAggregated(entity.getCustomer()));

		// aggregated
		entity.setInsurance(insurranceMerger.bindAggregated(entity
				.getInsurance()));

		// aggregated
		entity.setCreatingUser(loginMerger.bindAggregated(entity
				.getCreatingUser()));

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// aggregated
		entity.setSalesOrder(salesOrderMerger.bindAggregated(entity
				.getSalesOrder()));

		// composed collections
		Set<CustomerInvoiceItem> invoiceItems = entity.getInvoiceItems();
		for (CustomerInvoiceItem customerInvoiceItem : invoiceItems) {
			customerInvoiceItem.setInvoice(entity);
		}

		// aggregated collection
		paymentCustomerInvoiceAssocMerger.bindAggregated(entity.getPayments());

		return entity;
	}

	public void handleSalesClosed(@Observes @DocumentClosedEvent SalesOrder salesOrder){
		CustomerInvoice ci = new CustomerInvoice();

		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		ci.setCreatingUser(creatingUser);
		ci.setCreationDate(creationDate);
		ci.setAgency(salesOrder.getAgency());
		ci.setAmountBeforeTax(salesOrder.getAmountBeforeTax());
		ci.setTaxAmount(salesOrder.getAmountVAT());
		ci.setAmountAfterTax(salesOrder.getAmountAfterTax());
		ci.setAmountDiscount(salesOrder.getAmountDiscount());
		ci.setNetToPay(salesOrder.getAmountAfterTax().subtract(salesOrder.getAmountDiscount()));
		ci.setTotalRestToPay(ci.getNetToPay());
		ci.setCustomer(salesOrder.getCustomer());
		ci.setInsurance(salesOrder.getInsurance());
		
		BigDecimal insuranceCoverageRate = BigDecimal.ZERO;
		BigDecimal customerCoverageRate = BigDecimal.ONE;
		Insurrance insurance = salesOrder.getInsurance();
		if(insurance!=null){
			insuranceCoverageRate = salesOrder.getInsurance().getCoverageRate();
			customerCoverageRate = customerCoverageRate.subtract(insuranceCoverageRate);
		}
		ci.setCustomerRestTopay(ci.getNetToPay().multiply(customerCoverageRate));
		ci.setInsurranceRestTopay(ci.getNetToPay().multiply(insuranceCoverageRate));
		

		ci.setInvoiceNumber(RandomStringUtils.randomAlphanumeric(7));
		ci.setInvoiceType(InvoiceType.CASHDRAWER);
		ci.setSalesOrder(salesOrder);
		ci.setAdvancePayment(BigDecimal.ZERO);
		
		ci = create(ci);
		
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();
		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			CustomerInvoiceItem ciItem = new CustomerInvoiceItem();
			ciItem.setArticle(salesOrderItem.getArticle());
			ciItem.setInternalPic(salesOrderItem.getInternalPic());
			ciItem.setInvoice(ci);
			ciItem.setPurchasedQty(salesOrderItem.getOrderedQty().subtract(salesOrderItem.getReturnedQty()));
			ciItem.setSalesPricePU(salesOrderItem.getSalesPricePU());
			ciItem.setTotalSalesPrice(salesOrderItem.getTotalSalePrice());
			ciItem = customerInvoiceItemEJB.create(ciItem);
		}
	}

	/**
	 * If we cancel a closed sales, we must delete the corresponding invoice.
	 * 
	 * @param salesOrder
	 */
	@SuppressWarnings("unchecked")
	public void handleSalesCanceled(@Observes @DocumentCanceledEvent SalesOrder salesOrder){
		CustomerInvoice ci = new CustomerInvoice();
		ci.setSalesOrder(salesOrder);
		List<CustomerInvoice> found = findBy(ci, 0, 1, new SingularAttribute[]{CustomerInvoice_.salesOrder});
		ci = found.iterator().next();
		deleteById(ci.getId());
	}
	
	public void processDirectSales(@Observes @DirectSalesClosedEvent Payment payment){
		// The amount that went in or out of the cash drawer.
		BigDecimal amount = payment.getAmount();
		
		// Customer or insurance.
		Customer paidBy = payment.getPaidBy();
		
		// A direct sale must not have more than one invoice.
		Set<PaymentCustomerInvoiceAssoc> invoices = payment.getInvoices();
		if(invoices.size()>1) throw new IllegalStateException("Direct sale can not contain more than one invoice.");
		if(invoices.size()<1) throw new IllegalStateException("Direct sale can not contain less than one invoice.");
		PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc = invoices.iterator().next();
		
		CustomerInvoice customerInvoice = paymentCustomerInvoiceAssoc.getTarget();
		boolean insurancePaying =  customerInvoice.getInsurance()!=null && paidBy.equals(customerInvoice.getInsurance().getInsurer());
		if(customerInvoice.getCustomer().equals(customerInvoice.getInsurance().getInsurer())){
			throw new IllegalStateException("Inconsistent invoice, customer and insurer can not be identical.");
		}
		// BTW both might be true.

		BigDecimal totalRestToPay = customerInvoice.getTotalRestToPay();
		BigDecimal restToPay = null;
		if(insurancePaying){
			restToPay = customerInvoice.getInsurranceRestTopay();
		} else {
			restToPay = customerInvoice.getCustomerRestTopay();
		}
		if(restToPay.compareTo(BigDecimal.ZERO)<=0) throw new IllegalStateException("Invoice balance is zero or less. Can not pay a balanced invoice.");
		// The rest to pay if positive is equals or more than the amount.
		if(amount.compareTo(BigDecimal.ZERO)<0) throw new IllegalStateException("Payment amount is negative. Can not pay with a negative amount.");
		if(restToPay.compareTo(amount)<0) throw new IllegalStateException("Invoice amount less than payment amount. Balance will be negative.");
		restToPay = restToPay.subtract(amount);
		totalRestToPay = totalRestToPay.subtract(amount);
		customerInvoice.setTotalRestToPay(totalRestToPay);
		if(insurancePaying){
			customerInvoice.setInsurranceRestTopay(restToPay);
		} else {
			customerInvoice.setCustomerRestTopay(restToPay);
		}
		if(totalRestToPay.compareTo(BigDecimal.ZERO)==0){
			customerInvoice.setSettled(Boolean.TRUE);
			customerInvoice.setCashed(Boolean.TRUE);
		}
		customerInvoice = update(customerInvoice);
			
		// Announce customer invoice processed.
		customerInvoiceProcessedEvent.fire(customerInvoice);
	}

	public void processPayment(@Observes @DocumentProcessedEvent Payment payment){
		BigDecimal amount = payment.getAmount();
		Customer paidBy = payment.getPaidBy();
		
		Set<PaymentCustomerInvoiceAssoc> invoices = payment.getInvoices();
		for (PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc : invoices) {
			CustomerInvoice customerInvoice = paymentCustomerInvoiceAssoc.getTarget();
			if(amount.compareTo(BigDecimal.ZERO)<=0 ||// there is no money left for invoice settlement
				Boolean.TRUE.equals(customerInvoice.getCashed() || // invoice is cashed
				Boolean.TRUE.equals(customerInvoice.getSettled()))){ // invoice is settled
				continue;
			}
			if(paidBy==null || (!paidBy.equals(customerInvoice.getCustomer()) && !paidBy.equals(customerInvoice.getInsurance().getInsurer()))) continue;
			boolean customer = customerInvoice.getCustomer().equals(paidBy);

			BigDecimal totalRestToPay = customerInvoice.getTotalRestToPay();
			BigDecimal customerRestTopay = customer?customerInvoice.getCustomerRestTopay():customerInvoice.getInsurranceRestTopay();
			BigDecimal amountForThisInvoice = null;
			if(amount.compareTo(customerRestTopay)>0){
				amountForThisInvoice = amount.subtract(customerRestTopay);
				amount = amount.subtract(amountForThisInvoice);
			} else {
				amountForThisInvoice = amount;
				amount = BigDecimal.ZERO;
			}
			customerRestTopay = customerRestTopay.subtract(amountForThisInvoice);
			if(customer)
				customerInvoice.setCustomerRestTopay(customerRestTopay);
			else 
				customerInvoice.setInsurranceRestTopay(customerRestTopay);
			
			totalRestToPay = totalRestToPay.subtract(amountForThisInvoice);
			customerInvoice.setTotalRestToPay(totalRestToPay);
			if(totalRestToPay.compareTo(BigDecimal.ZERO)<=0){
				customerInvoice.setSettled(Boolean.TRUE);
				customerInvoice.setCashed(Boolean.TRUE);
			}
			customerInvoice = update(customerInvoice);
			
			// Announce customer invoice processed.
			customerInvoiceProcessedEvent.fire(customerInvoice);

		}
	}
}
