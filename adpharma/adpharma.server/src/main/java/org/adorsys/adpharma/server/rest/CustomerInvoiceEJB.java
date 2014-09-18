package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.AgencyByNumberComparator;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLot_;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.CustomerInvoice_;
import org.adorsys.adpharma.server.jpa.DebtStatement;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.jpa.InvoiceByAgencyPrintInput;
import org.adorsys.adpharma.server.jpa.InvoiceType;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.jpa.PaymentItem;
import org.adorsys.adpharma.server.jpa.PaymentItemComparator;
import org.adorsys.adpharma.server.jpa.PaymentItemCustomerInvoiceAssoc;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchInput;
import org.adorsys.adpharma.server.jpa.SalesStatisticsDataSearchResult;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.repo.CustomerInvoiceRepository;
import org.adorsys.adpharma.server.repo.DebtStatementCustomerInvoiceAssocRepository;
import org.adorsys.adpharma.server.repo.PaymentItemCustomerInvoiceAssocRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.AdTimeFrame;
import org.adorsys.adpharma.server.utils.ChartData;
import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CustomerInvoiceEJB {

	@Inject
	private CustomerInvoiceRepository repository;

	@Inject
	private CustomerMerger customerMerger;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private ArticleLotEJB articleLotEJB ;

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

	@Inject
	PaymentItemCustomerInvoiceAssocRepository paymentItemCustomerInvoiceAssocRepository;

	public CustomerInvoice create(CustomerInvoice entity) {
		CustomerInvoice save = repository.save(attach(entity)); 
		save.setInvoiceNumber((SequenceGenerator.CUSTOMER_INVOICE_SEQUENCE_PREFIXE+save.getId()));
		return repository.save(save);
	}

	public  List<CustomerInvoice> findUnpayInvoiceByInsurer(Customer insurer){
		return repository.findUnpayInvoiceByCustomer(insurer, Boolean.TRUE, Boolean.FALSE, InvoiceType.CASHDRAWER);
	}



	@Inject
	private EntityManager em ;

	@Inject
	DebtStatementCustomerInvoiceAssocRepository dsciar ;
	public List<CustomerInvoice> findCustomerInvoiceBySource(DebtStatement source){
		return dsciar.findCustomerInvoiceBySource(source);
	}

	//	public List<CustomerInvoice> findByAgencyAndDateBetween(InvoiceByAgencyPrintInput searchInput){
	//		if(searchInput.getAgency()==null||searchInput.getAgency().getId()==null)
	//			return repository.findByDateBetweenAndCashed(searchInput.getFromDate(), searchInput.getToDate(), Boolean.TRUE, InvoiceType.VOUCHER);
	//		return repository.findByAgencyAndDateBetween(searchInput.getFromDate(), searchInput.getToDate(), searchInput.getAgency(), Boolean.TRUE, InvoiceType.VOUCHER);
	//	}
	//
	//	public List<CustomerInvoice> customerInvicePerDayAndPerAgency(InvoiceByAgencyPrintInput searchInput){
	//		List<CustomerInvoice> customerInvoices = new ArrayList<CustomerInvoice>();
	//		String query ="SELECT SUM(c.netToPay) , SUM(c.amountDiscount) ,   SUM(c.advancePayment) ,  SUM(c.totalRestToPay) , DATE(c.creationDate) FROM CustomerInvoice AS c WHERE c.creationDate BETWEEN :fromDate AND :toDate ";
	//
	//		if(searchInput.getAgency()!=null)
	//			query = query+ " AND c.agency = :agency ";
	//		query = query+" AND c.cashed = :cashed OR c.invoiceType = :invoiceType GROUP BY DATE(c.creationDate) " ;
	//
	//		Query querys = em.createQuery(query) ;
	//
	//		querys.setParameter("fromDate", searchInput.getFromDate());
	//		querys.setParameter("toDate", searchInput.getFromDate());
	//		querys.setParameter("cashed", Boolean.TRUE);
	//		querys.setParameter("invoiceType",InvoiceType.VOUCHER);
	//		if(searchInput.getAgency()!=null)
	//			querys.setParameter("agency",searchInput.getAgency());
	//		List<Object[]> resultList = querys.getResultList();
	//		for (Object[] objects : resultList) {
	//			CustomerInvoice invoice = new CustomerInvoice();
	//			BigDecimal netTopay = (BigDecimal) objects[0];
	//			BigDecimal amountDiscount = (BigDecimal) objects[1];
	//			BigDecimal advancePayment = (BigDecimal) objects[2];
	//			BigDecimal totalRestToPay = (BigDecimal) objects[3];
	//			Date date = (Date) objects[4];
	//			invoice.setNetToPay(netTopay);
	//			invoice.setAmountDiscount(amountDiscount);
	//			invoice.setAdvancePayment(advancePayment);
	//			invoice.setTotalRestToPay(totalRestToPay);
	//			invoice.setCreationDate(date);
	//			customerInvoices.add(invoice);
	//
	//		}
	//
	//		return customerInvoices ;
	//	}

	public SalesStatisticsDataSearchResult findSalesStatistics(SalesStatisticsDataSearchInput dataSearchInput){
		List<ChartData> chartDataSearchResult = new ArrayList<ChartData>();
		String query ="SELECT SUM(c.netToPay) , MONTHNAME(c.creationDate) FROM CustomerInvoice AS c WHERE YEAR(c.creationDate) = :creationDate " ;
		if(dataSearchInput.getCustomer()!=null)
			query = query+" AND c.customer  = :customer " ;
		query = query+" AND (c.cashed = :cashed OR c.invoiceType = :invoiceType) GROUP BY MONTHNAME(c.creationDate) " ;
		Query querys = em.createQuery(query) ;

		if(dataSearchInput.getCustomer()!=null)
			querys.setParameter("customer", dataSearchInput.getCustomer());

		querys.setParameter("creationDate", dataSearchInput.getYears());
		querys.setParameter("cashed", Boolean.TRUE);
		querys.setParameter("invoiceType", InvoiceType.VOUCHER);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = querys.getResultList();
		for (Object[] objects : resultList) {
			BigDecimal netTopay = (BigDecimal) objects[0];
			String month =  (String) objects[1];
			ChartData chartData = new ChartData(month, netTopay);
			chartDataSearchResult.add(chartData);
		}

		SalesStatisticsDataSearchResult searchResult = new SalesStatisticsDataSearchResult();
		searchResult.setChartData(chartDataSearchResult);

		return searchResult ;

	}

	public List<CustomerInvoice> findInsurranceCustomerInvoiceByDateBetween(InvoiceByAgencyPrintInput searchInput){
		String query ="SELECT c  FROM CustomerInvoice AS c WHERE c.cashed = :cashed AND c.insurance IS NOT NULL" ;

		if(searchInput.getFromDate()!=null)
			query = query+" AND c.creationDate >= :startTime" ;
		if(searchInput.getToDate()!=null)
			query = query+" AND c.creationDate <= :endTime" ;


		Query querys = em.createQuery(query,CustomerInvoice.class) ;
		querys.setParameter("cashed", Boolean.TRUE);
		if(searchInput.getFromDate()!=null)
			querys.setParameter("startTime", searchInput.getFromDate());
		if(searchInput.getToDate()!=null)
			querys.setParameter("endTime", searchInput.getToDate());
		List<CustomerInvoice> resultList = querys.getResultList();
		return resultList ;
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
			int max, SingularAttribute<CustomerInvoice, Object>[] attributes) {
		CustomerInvoice customerInvoice = attach(entity);
		return repository.criteriafindBy(customerInvoice, attributes).orderDesc(CustomerInvoice_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countBy(CustomerInvoice entity,
			SingularAttribute<CustomerInvoice, ?>[] attributes) {
		CustomerInvoice customerInvoice = attach(entity);
		return repository.count(customerInvoice, attributes);
	}

	public List<CustomerInvoice> findByLike(CustomerInvoice entity, int start,
			int max, SingularAttribute<CustomerInvoice, Object>[] attributes) {
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

		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();
		HashMap<Agency, CustomerInvoice> agencyInvoiceMap = new HashMap<Agency, CustomerInvoice>();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			ArticleLot articleLot = new ArticleLot();
			articleLot.setInternalPic(salesOrderItem.getInternalPic());
			@SuppressWarnings("unchecked")
			List<ArticleLot> found = articleLotEJB.findBy(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic});
			if(found.isEmpty())
				continue ;
			Agency agency = found.iterator().next().getAgency();
			if(agencyInvoiceMap.containsKey(agency)){
				CustomerInvoice agencyInvoice = agencyInvoiceMap.get(agency);

				CustomerInvoiceItem ciItem = createCustoerInvoiceItem(salesOrderItem, agencyInvoice);

				updateCustomerInvoiceAmounts(ciItem, agencyInvoice);

			}else {
				CustomerInvoice ci = new CustomerInvoice();
				// intialize the sales order
				ci = initCustomerInvoice(ci, agency, salesOrder, "CI", InvoiceType.CASHDRAWER);

				CustomerInvoiceItem ciItem = createCustoerInvoiceItem(salesOrderItem, ci);

				updateCustomerInvoiceAmounts(ciItem, ci);

				agencyInvoiceMap.put(agency, ci);
			}
		}

		processInvoices(agencyInvoiceMap.values(), salesOrder);
	}

	private Collection<CustomerInvoice> processInvoices(Collection<CustomerInvoice> invoices, SalesOrder salesOrder){
		BigDecimal amountAfterTax = salesOrder.getAmountAfterTax();
		int invoiceSize = invoices.size();
		// DIscount will be shared proportionally to all invoices.
		BigDecimal amountDiscount = salesOrder.getAmountDiscount();
		BigDecimal discountRate = amountDiscount.divide(amountAfterTax, 8, RoundingMode.HALF_EVEN);

		Collection<CustomerInvoice> result = new ArrayList<CustomerInvoice>(invoices.size());
		for (CustomerInvoice customerInvoice : invoices) {

			BigDecimal discount = customerInvoice.getAmountAfterTax().multiply(discountRate);
			if(invoiceSize==1)
				discount =amountDiscount ;
			customerInvoice.setAmountDiscount(discount);
			customerInvoice.setNetToPay(customerInvoice.getAmountAfterTax().subtract(discount));

			BigDecimal insuranceCoverageRate = BigDecimal.ZERO;
			BigDecimal customerCoverageRate = BigDecimal.ONE;
			Insurrance insurance = customerInvoice.getInsurance();

			if(insurance!=null){
				insuranceCoverageRate = customerInvoice.getInsurance().getCoverageRate().divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_EVEN);
				customerCoverageRate = customerCoverageRate.subtract(insuranceCoverageRate);
			}
			customerInvoice.setCustomerRestTopay(customerInvoice.getNetToPay().multiply(customerCoverageRate));
			customerInvoice.setInsurranceRestTopay(customerInvoice.getNetToPay().multiply(insuranceCoverageRate));
			customerInvoice.setAdvancePayment(BigDecimal.ZERO);
			customerInvoice.setTotalRestToPay(customerInvoice.getNetToPay().subtract(customerInvoice.getAdvancePayment()));
			customerInvoice = update(customerInvoice);
			result.add(customerInvoice);
		}

		return result;
	}

	private void updateCustomerInvoiceAmounts(CustomerInvoiceItem ciItem, CustomerInvoice ci){
		// This is amount after tax. Tax is always included in the unit price.
		BigDecimal amountAfterTax = ciItem.getTotalSalesPrice();

		ci.setAmountAfterTax(ci.getAmountAfterTax().add(amountAfterTax));

		VAT vat = ciItem.getArticle().getVat();
		BigDecimal vatRateRaw = BigDecimal.ZERO;
		if(vat!=null)vatRateRaw = VAT.getRawRate(vat.getRate());
		BigDecimal amountBeforeTax = amountAfterTax.divide(BigDecimal.ONE.add(vatRateRaw), 4, RoundingMode.HALF_EVEN);
		ci.setAmountBeforeTax(ci.getAmountBeforeTax().add(amountBeforeTax));

		BigDecimal taxAmount = amountAfterTax.subtract(amountBeforeTax);
		ci.setTaxAmount(ci.getTaxAmount().add(taxAmount));
	}

	private CustomerInvoiceItem createCustoerInvoiceItem(SalesOrderItem salesOrderItem, CustomerInvoice ci){
		CustomerInvoiceItem ciItem = new CustomerInvoiceItem();
		ciItem.setArticle(salesOrderItem.getArticle());
		ciItem.setInternalPic(salesOrderItem.getInternalPic());
		ciItem.setInvoice(ci);
		if(salesOrderItem.getReturnedQty()!=null && salesOrderItem.getReturnedQty().compareTo(BigDecimal.ZERO)>0){
			// Return
			ciItem.setPurchasedQty(salesOrderItem.getReturnedQty().negate());
		} else {
			// Sale
			ciItem.setPurchasedQty(salesOrderItem.getDeliveredQty());
		}
		ciItem.setSalesPricePU(salesOrderItem.getSalesPricePU());
		ciItem.setPurchasePricePU(salesOrderItem.getPurchasePricePU());
		ciItem.setTotalSalesPrice(ciItem.getSalesPricePU().multiply(ciItem.getPurchasedQty()));
		return customerInvoiceItemEJB.create(ciItem);
	}

	private CustomerInvoice initCustomerInvoice(CustomerInvoice ci, Agency agency, SalesOrder salesOrder, String invoicePrefix, InvoiceType invoiceType){

		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();

		ci.setCreationDate(creationDate);
		ci.setAgency(agency);
		ci.setAmountBeforeTax(BigDecimal.ZERO);
		ci.setTaxAmount(BigDecimal.ZERO);
		ci.setAmountAfterTax(BigDecimal.ZERO);
		ci.setAmountDiscount(BigDecimal.ZERO);
		ci.setNetToPay(BigDecimal.ZERO);
		ci.setTotalRestToPay(BigDecimal.ZERO);
		ci.setCustomer(salesOrder.getCustomer());
		ci.setInsurance(salesOrder.getInsurance());
		ci.setInvoiceNumber(salesOrder.getSoNumber().replace("SO", invoicePrefix)+"-"+agency.getId());
		ci.setInvoiceType(invoiceType);
		ci.setSalesOrder(salesOrder);
		ci.setAdvancePayment(BigDecimal.ZERO);
		String patientMatricle = StringUtils.isNotBlank(salesOrder.getPatientMatricle())?salesOrder.getPatientMatricle():salesOrder.getCustomer().getSerialNumber();
		ci.setPatientMatricle(patientMatricle);
		ci.setCreatingUser(salesOrder.getSalesAgent());
		return create(ci);

	}

	/**
	 *generate Customer invoice Of Type Voucher and voucher .
	 * 
	 * @param salesOrder
	 */
	public void handleReturnSales(@Observes @ReturnSalesEvent SalesOrder salesOrder){
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();
		HashMap<Agency, CustomerInvoice> agencyInvoiceMap = new HashMap<Agency, CustomerInvoice>();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			// no invoice if returned quantity is zero.
			if(salesOrderItem.getReturnedQty()==null || 
					salesOrderItem.getReturnedQty().compareTo(BigDecimal.ZERO)==0) continue;

			ArticleLot articleLot = new ArticleLot();
			articleLot.setInternalPic(salesOrderItem.getInternalPic());
			@SuppressWarnings("unchecked")
			List<ArticleLot> found = articleLotEJB.findBy(articleLot, 0, 1, new SingularAttribute[]{ArticleLot_.internalPic});
			if(found.isEmpty())
				continue ;
			Agency agency = found.iterator().next().getAgency();
			if(agencyInvoiceMap.containsKey(agency)){
				CustomerInvoice agencyInvoice = agencyInvoiceMap.get(agency);

				CustomerInvoiceItem ciItem = createCustoerInvoiceItem(salesOrderItem, agencyInvoice);

				updateCustomerInvoiceAmounts(ciItem, agencyInvoice);

			}else {
				CustomerInvoice ci = new CustomerInvoice();
				agencyInvoiceMap.put(agency, ci);
				// intialize the sales order
				ci = initCustomerInvoice(ci, agency, salesOrder, "CR", InvoiceType.VOUCHER);

				CustomerInvoiceItem ciItem = createCustoerInvoiceItem(salesOrderItem, ci);

				updateCustomerInvoiceAmounts(ciItem, ci);
			}
		}
		Collection<CustomerInvoice> processedInvoices = processInvoices(agencyInvoiceMap.values(), salesOrder);
		for (CustomerInvoice customerInvoice : processedInvoices) {
			customerInvoiceReturnSalesEvent.fire(customerInvoice);
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
		if(!found.isEmpty()){
			ci = found.iterator().next();
			deleteById(ci.getId());
		}
	}

	public void processPayment(@Observes @DocumentProcessedEvent Payment payment){
		Set<PaymentCustomerInvoiceAssoc> invoices = payment.getInvoices();
		Set<PaymentItem> paymentItems = payment.getPaymentItems();
		ArrayList<PaymentItem> paymentItemList = new ArrayList<PaymentItem>(paymentItems);
		Collections.sort(paymentItemList, new PaymentItemComparator());

		Map<Agency, List<PaymentCustomerInvoiceAssoc>> byAgency = new HashMap<Agency, List<PaymentCustomerInvoiceAssoc>>();
		List<Agency> agencies = new ArrayList<Agency>();
		for (PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc : invoices) {
			CustomerInvoice customerInvoice = paymentCustomerInvoiceAssoc.getTarget();
			if(!customerInvoice.getCashed()){
				customerInvoiceClosedEvent.fire(customerInvoice);
				customerInvoice.setCashed(Boolean.TRUE);
			}else {
				continue;
			}

			// Sort those customer invoices by agency.
			CustomerInvoice ci = paymentCustomerInvoiceAssoc.getTarget();
			Agency agency = ci.getAgency();
			if(!agencies.contains(agency)) agencies.add(agency);
			List<PaymentCustomerInvoiceAssoc> list = byAgency.get(agency);
			if(list==null){
				list = new ArrayList<PaymentCustomerInvoiceAssoc>();
				byAgency.put(agency, list);
			}
			list.add(paymentCustomerInvoiceAssoc);
		}		
		Collections.sort(agencies, new AgencyByNumberComparator());
		Collections.reverse(agencies);

		for (PaymentItem paymentItem : paymentItemList) {
			Customer payer = paymentItem.getPaidBy();
			BigDecimal amount = paymentItem.getAmount();
			for (Agency agency : agencies) {
				List<PaymentCustomerInvoiceAssoc> assocs = byAgency.get(agency);

				for (PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc : assocs) {
					CustomerInvoice customerInvoice = paymentCustomerInvoiceAssoc.getTarget();

					if(Boolean.TRUE.equals(customerInvoice.getSettled())) continue;
					if(amount.compareTo(BigDecimal.ZERO)<=0) continue;

					boolean customer = true;// paid by the client
					// just client use this methode for payment there is another methode for insurance payement @see PaymentEJB.createPaymentForDebtstatement
//					if(customerInvoice.getInsurance()!=null && customerInvoice.getInsurance().getInsurer().equals(payer))customer=false;// paid by the insurance.

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

					PaymentItemCustomerInvoiceAssoc piciAssoc = new PaymentItemCustomerInvoiceAssoc();
					piciAssoc.setAgency(agency);
					piciAssoc.setCashDrawer(payment.getCashDrawer());
					piciAssoc.setAmount(amountForThisInvoice);
					piciAssoc.setCustomerInvoice(customerInvoice);
					piciAssoc.setPaymentItem(paymentItem);
					piciAssoc.setPaymentMode(paymentItem.getPaymentMode());
					paymentItemCustomerInvoiceAssocRepository.save(piciAssoc);

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
}