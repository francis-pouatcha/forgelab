package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;

public class ReceiptPrinterData {

	public String getCustomerName() {
		return customerName;
	}

	private final String customerName ;
	private final Payment payment;
	private final Agency agency;
	private final Company company;
	private final Login cashier;
	private  String salesOrderNumber;
	private final List<CustomerInvoicePrinterData> invoiceData = new ArrayList<CustomerInvoicePrinterData>();
	private final List<CustomerVoucher> usedVouchers  = new ArrayList<CustomerVoucher>() ;
	public ReceiptPrinterData(Payment payment, Agency agency, Company company, Login cashier,String customerName) {
		super();
		this.payment = payment;
		this.agency = agency;
		this.company = company;
		this.cashier = cashier;
		this.customerName = customerName;
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

	public List<CustomerVoucher> getUsedVouchers() {
		return usedVouchers;
	}

	public String getSalesOrderNumber() {
		return salesOrderNumber;
	}

	public void setSalesOrderNumber(String salesOrderNumber) {
		this.salesOrderNumber = salesOrderNumber;
	}


}
