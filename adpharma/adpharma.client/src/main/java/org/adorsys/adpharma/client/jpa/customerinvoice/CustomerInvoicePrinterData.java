package org.adorsys.adpharma.client.jpa.customerinvoice;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.client.jpa.login.Login;

public class CustomerInvoicePrinterData {

	private final CustomerInvoice customerInvoice;
	private final Company company;
	private final Agency agency;
	private final Login login;
	private CustomerInvoiceItemSearchResult customerInvoiceItemSearchResult;

	public CustomerInvoicePrinterData(CustomerInvoice customerInvoice,
			Company company, Agency agency, Login login, CustomerInvoiceItemSearchResult customerInvoiceItemSearchResult) {
		super();
		this.customerInvoice = customerInvoice;
		this.company = company;
		this.agency = agency;
		this.login = login;
		this.customerInvoiceItemSearchResult = customerInvoiceItemSearchResult;
	}
	public CustomerInvoice getCustomerInvoice() {
		return customerInvoice;
	}
	public Company getCompany() {
		return company;
	}
	public Agency getAgency() {
		return agency;
	}
	public Login getLogin() {
		return login;
	}
	public CustomerInvoiceItemSearchResult getCustomerInvoiceItemSearchResult() {
		return customerInvoiceItemSearchResult;
	}
	public void setCustomerInvoiceItemSearchResult(
			CustomerInvoiceItemSearchResult customerInvoiceItemSearchResult) {
		this.customerInvoiceItemSearchResult = customerInvoiceItemSearchResult;
	}
}
