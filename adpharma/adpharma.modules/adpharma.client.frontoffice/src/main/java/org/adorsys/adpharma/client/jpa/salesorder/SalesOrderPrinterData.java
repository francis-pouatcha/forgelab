package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchResult;

public class SalesOrderPrinterData {

	private final SalesOrder salesOrder;
	private final Company company;
	private final Agency agency;
	private final Login login;
	private SalesOrderItemSearchResult salesOrderItemSearchResult;

	public SalesOrderPrinterData(SalesOrder salesOrder,
			Company company, Agency agency, Login login, SalesOrderItemSearchResult salesOrderItemSearchResult) {
		super();
		this.salesOrder = salesOrder;
		this.company = company;
		this.agency = agency;
		this.login = login;
		this.salesOrderItemSearchResult = salesOrderItemSearchResult;
	}
	public SalesOrder getSalesOrder() {
		return salesOrder;
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
	public SalesOrderItemSearchResult getSalesOrderItemSearchResult() {
		return salesOrderItemSearchResult;
	}
	public void setSalesOrderItemSearchResult(
			SalesOrderItemSearchResult salesOrderItemSearchResult) {
		this.salesOrderItemSearchResult = salesOrderItemSearchResult;
	}
}
