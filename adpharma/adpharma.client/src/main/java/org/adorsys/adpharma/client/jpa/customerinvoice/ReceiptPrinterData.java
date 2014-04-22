package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;

public class ReceiptPrinterData {

	private final Payment payment;
	private final Agency agency;
	private final Company company;
	private final Login cashier;
	private final List<CustomerInvoicePrinterData> invoiceData = new ArrayList<CustomerInvoicePrinterData>();

	public ReceiptPrinterData(Payment payment, Agency agency, Company company, Login cashier) {
		super();
		this.payment = payment;
		this.agency = agency;
		this.company = company;
		this.cashier = cashier;
	}

	public List<CustomerInvoicePrinterData> getInvoiceData() {
		return invoiceData;
	}

	public Payment getPayment() {
		return payment;
	}

	public Agency getAgency() {
		return agency;
	}

	public Company getCompany() {
		return company;
	}

	public Login getCashier() {
		return cashier;
	}
	
	
}
