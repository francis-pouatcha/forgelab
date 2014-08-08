package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.events.PaymentId;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencyService;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.company.CompanyService;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemService;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchByPaiementIdService;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchService;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginService;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.payment.PaymentService;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssocSearchInput;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssocSearchResult;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssocService;

public class ReceiptPrinterDataService extends
Service<ReceiptPrinterData> {

	@Inject
	private PaymentService paymentService;
	@Inject
	private PaymentCustomerInvoiceAssocService paymentCustomerInvoiceAssocService;
	@Inject
	private CustomerInvoiceService customerInvoiceService;
	@Inject
	private CompanyService companyService;
	@Inject
	private AgencyService agencyService;
	@Inject
	private LoginService loginService;
	@Inject
	private CustomerInvoiceItemService customerInvoiceItemService;
	
	@Inject
	private CustomerVoucherService voucherService;

	private PaymentId paymentId;

	public ReceiptPrinterDataService setPaymentId(PaymentId paymentId) {
		this.paymentId = paymentId;
		return this;
	}

	@Override
	protected Task<ReceiptPrinterData> createTask() {
		return new Task<ReceiptPrinterData>() {
			@Override
			protected ReceiptPrinterData call() throws Exception {
				if (paymentId == null)
					return null;

				Payment payment = paymentService.findById(paymentId.getId());
				Agency agency = agencyService.findById(payment.getAgency().getId());
				Company company = companyService.findById(agency.getCompany().getId());
				Login cashier = loginService.findById(payment.getCashier().getId());
				List<CustomerVoucher> usedVoucher = voucherService.findByPaiementId(paymentId.getId()).getResultList();
				ReceiptPrinterData result = new ReceiptPrinterData(payment, agency, company, cashier,paymentId.getCustomerName(),paymentId.isPrintWhithoutDiscount());
				result.getUsedVouchers().addAll(usedVoucher);
				PaymentCustomerInvoiceAssocSearchInput searchInput = new PaymentCustomerInvoiceAssocSearchInput();
				int max = 100;
				searchInput.setMax(max);
				PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc = new PaymentCustomerInvoiceAssoc();
				paymentCustomerInvoiceAssoc.setSource(payment);
				searchInput.setEntity(paymentCustomerInvoiceAssoc);
				searchInput.getFieldNames().add("source");
				PaymentCustomerInvoiceAssocSearchResult paymentCustomerInvoiceAssocSearchResult = paymentCustomerInvoiceAssocService.findBy(searchInput);
				if(!paymentCustomerInvoiceAssocSearchResult.getResultList().isEmpty()) {
					String soNumber = paymentCustomerInvoiceAssocSearchResult.getResultList().iterator().next().getTarget().getSalesOrder().getSoNumber();
					result.setSalesOrderNumber(soNumber);
				}
				Long count = paymentCustomerInvoiceAssocSearchResult.getCount();
				int start = paymentCustomerInvoiceAssocSearchResult.getSearchInput().getStart();
				while (start<count) {
					List<PaymentCustomerInvoiceAssoc> resultList = paymentCustomerInvoiceAssocSearchResult.getResultList();
					List<CustomerInvoicePrinterData> invoiceData = processResultList(resultList);
					result.getInvoiceData().addAll(invoiceData);
					start+=max;
					searchInput.setStart(start);
					paymentCustomerInvoiceAssocSearchResult = paymentCustomerInvoiceAssocService.findBy(searchInput);
				}
				return result;
			}

			private List<CustomerInvoicePrinterData> processResultList(List<PaymentCustomerInvoiceAssoc> resultList){
				List<CustomerInvoicePrinterData> result = new ArrayList<CustomerInvoicePrinterData>();
				for (PaymentCustomerInvoiceAssoc paymentCustomerInvoiceAssoc : resultList) {
					CustomerInvoice customerInvoice = paymentCustomerInvoiceAssoc.getTarget();
					customerInvoice = customerInvoiceService.findById(customerInvoice.getId());

					Agency agency = agencyService.findById(customerInvoice.getAgency().getId());
					Company company = companyService.findById(agency.getCompany().getId());
					Login login = loginService.findById(customerInvoice.getCreatingUser().getId());

					CustomerInvoiceItem customerInvoiceItem = new CustomerInvoiceItem();
					customerInvoiceItem.setInvoice(new CustomerInvoiceItemInvoice(
							customerInvoice));
					CustomerInvoiceItemSearchInput searchInput = new CustomerInvoiceItemSearchInput();
					searchInput.setEntity(customerInvoiceItem);
					searchInput.getFieldNames().add("invoice");
					CustomerInvoiceItemSearchResult found = customerInvoiceItemService
							.findBy(searchInput);
					result.add(new CustomerInvoicePrinterData(customerInvoice,
							company, agency, login, found));
				}
				return result;
			}
		};
	}
}
