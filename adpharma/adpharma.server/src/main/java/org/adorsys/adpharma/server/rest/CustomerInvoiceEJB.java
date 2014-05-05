package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.events.ReturnSalesEvent;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.CustomerInvoice_;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.jpa.InvoiceType;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.jpa.PaymentItem;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.repo.CustomerInvoiceRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.ChartData;
import org.adorsys.adpharma.server.utils.ChartDataSearchInput;
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
	
	@Inject
	@ReturnSalesEvent
	private Event<CustomerInvoice> customerInvoiceReturnSalesEvent;

	@Inject
	@DirectSalesClosedEvent
	private Event<CustomerInvoice> customerInvoiceClosedEvent;
	
	public CustomerInvoice create(CustomerInvoice entity) {
		return repository.save(attach(entity));
	}
	

	@Inject
	private EntityManager em ;
	
	public List<ChartData> findSalesStatistics(ChartDataSearchInput dataSearchInput){
		List<ChartData> chartDataSearchResult = new ArrayList<ChartData>();
		Query query = em.createNativeQuery("SELECT SUM(c.netToPay) AS NET , MONTH(c.creationDate) AS SALES_MONTH FROM CustomerInvoice AS c WHERE YEAR(c.creationDate) = :creationDate "
				+ " AND (c.cashed = :cashed OR c.invoiceType = :invoiceType) GROUP BY SALES_MONTH ") ;
		query.setParameter("creationDate", dataSearchInput.getYears());
		query.setParameter("cashed", Boolean.TRUE);
		query.setParameter("invoiceType", InvoiceType.VOUCHER);
		List<Object[]> resultList = query.getResultList();
		for (Object[] objects : resultList) {
			BigDecimal netTopay = (BigDecimal) objects[0];
			int month =  (int) objects[1];
			ChartData chartData = new ChartData(month+"", netTopay);
			chartDataSearchResult.add(chartData);
		}
		
		
		return chartDataSearchResult ;
		
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
		CustomerInvoice customerInvoice = attach(entity);
		return repository.findBy(customerInvoice, start, max, attributes);
	}

	public Long countBy(CustomerInvoice entity,
			SingularAttribute<CustomerInvoice, ?>[] attributes) {
		CustomerInvoice customerInvoice = attach(entity);
		return repository.count(customerInvoice, attributes);
	}

	public List<CustomerInvoice> findByLike(CustomerInvoice entity, int start,
			int max, SingularAttribute<CustomerInvoice, ?>[] attributes) {
		CustomerInvoice customerInvoice = attach(entity);
		return repository.findByLike(customerInvoice, start, max, attributes);
	}

	public Long countByLike(CustomerInvoice entity,
			SingularAttribute<CustomerInvoice, ?>[] attributes) {
		CustomerInvoice customerInvoice = attach(entity);
		return repository.countLike(customerInvoice, attributes);
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
			insuranceCoverageRate = salesOrder.getInsurance().getCoverageRate().divide(BigDecimal.valueOf(100));
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
	 *generate Customer invoice Of Type Voucher and voucher .
	 * 
	 * @param salesOrder
	 */
	public void handleReturnSales(@Observes @ReturnSalesEvent SalesOrder salesOrder){
		CustomerInvoice ci = new CustomerInvoice();

		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		ci.setCreatingUser(creatingUser);
		ci.setCreationDate(creationDate);
		ci.setAgency(salesOrder.getAgency());
		ci.setAmountBeforeTax(salesOrder.getTotalReturnAmount().negate());
		ci.setTaxAmount(BigDecimal.ZERO);
		ci.setAmountAfterTax(salesOrder.getTotalReturnAmount().negate());
		ci.setAmountDiscount(BigDecimal.ZERO);
		ci.setNetToPay(salesOrder.getTotalReturnAmount().negate());
		ci.setTotalRestToPay(ci.getNetToPay());
		ci.setCustomer(salesOrder.getCustomer());
		ci.setInsurance(salesOrder.getInsurance());
		
		BigDecimal insuranceCoverageRate = BigDecimal.ZERO;
		BigDecimal customerCoverageRate = BigDecimal.ONE;
		Insurrance insurance = salesOrder.getInsurance();
		if(insurance!=null){
			insuranceCoverageRate = (salesOrder.getInsurance().getCoverageRate()).divide(BigDecimal.valueOf(100));
			customerCoverageRate = customerCoverageRate.subtract(insuranceCoverageRate);
		}
		ci.setCustomerRestTopay(ci.getNetToPay().multiply(customerCoverageRate));
		ci.setInsurranceRestTopay(ci.getNetToPay().multiply(insuranceCoverageRate));
		

		ci.setInvoiceNumber(RandomStringUtils.randomAlphanumeric(7));
		ci.setInvoiceType(InvoiceType.VOUCHER);
		ci.setSalesOrder(salesOrder);
		ci.setAdvancePayment(BigDecimal.ZERO);
		
		ci = create(ci);
		
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();
		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			CustomerInvoiceItem ciItem = new CustomerInvoiceItem();
			ciItem.setArticle(salesOrderItem.getArticle());
			ciItem.setInternalPic(salesOrderItem.getInternalPic());
			ciItem.setInvoice(ci);
			ciItem.setPurchasedQty(salesOrderItem.getReturnedQty().negate());
			ciItem.setSalesPricePU(salesOrderItem.getSalesPricePU());
			ciItem.setTotalSalesPrice(ciItem.getSalesPricePU().multiply(salesOrderItem.getReturnedQty()));
			ciItem = customerInvoiceItemEJB.create(ciItem);
		}
		CustomerInvoice update = update(ci);
		customerInvoiceReturnSalesEvent.fire(update);
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

	public void processPayment(@Observes @DocumentProcessedEvent Payment payment){
		Set<PaymentCustomerInvoiceAssoc> invoices = payment.getInvoices();
		Set<PaymentItem> paymentItems = payment.getPaymentItems();
		for (PaymentItem paymentItem : paymentItems) {
			Customer payer = paymentItem.getPaidBy();
			BigDecimal amount = paymentItem.getAmount();
			for (PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc : invoices) {
				CustomerInvoice customerInvoice = paymentCustomerInvoiceAssoc.getTarget();
				if(!customerInvoice.getCashed()){
					customerInvoiceClosedEvent.fire(customerInvoice);
					customerInvoice.setCashed(Boolean.TRUE);
				}
				
				if(Boolean.TRUE.equals(customerInvoice.getSettled())) continue;
				if(amount.compareTo(BigDecimal.ZERO)<=0) continue;
				
				boolean customer = true;// paid by the client
				if(customerInvoice.getInsurance()!=null && customerInvoice.getInsurance().getCustomer().equals(payer))customer=false;// paid by the insurance.

				
				BigDecimal totalRestToPay = customerInvoice.getTotalRestToPay();
				BigDecimal payerRestTopay = customer?customerInvoice.getCustomerRestTopay():customerInvoice.getInsurranceRestTopay();
				if(payerRestTopay.compareTo(BigDecimal.ZERO)<=0) continue;
				
				BigDecimal amountForThisInvoice = null;
				if(amount.compareTo(payerRestTopay)>0){
					amountForThisInvoice = amount.subtract(payerRestTopay);
					amount = amount.subtract(amountForThisInvoice);
				} else {
					amountForThisInvoice = amount;
					amount = BigDecimal.ZERO;
				}
				payerRestTopay = payerRestTopay.subtract(amountForThisInvoice);
				if(customer)
					customerInvoice.setCustomerRestTopay(payerRestTopay);
				else 
					customerInvoice.setInsurranceRestTopay(payerRestTopay);
				
				totalRestToPay = totalRestToPay.subtract(amountForThisInvoice);
				customerInvoice.setTotalRestToPay(totalRestToPay);
				if(totalRestToPay.compareTo(BigDecimal.ZERO)<=0){
					customerInvoice.setSettled(Boolean.TRUE);
				}
				customerInvoice.setAdvancePayment(customerInvoice.getNetToPay().subtract(customerInvoice.getTotalRestToPay()));
				customerInvoice = update(customerInvoice);
				
				// Announce customer invoice processed.
				customerInvoiceProcessedEvent.fire(customerInvoice);
			}
		}
	}
}
