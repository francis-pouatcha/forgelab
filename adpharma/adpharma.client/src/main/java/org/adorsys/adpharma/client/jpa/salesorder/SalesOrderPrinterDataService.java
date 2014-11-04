package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencyService;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.company.CompanyService;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemService;

public class SalesOrderPrinterDataService extends Service<SalesOrderPrinterData>
{

	@Inject
	private SalesOrderService salesOrderService;
	@Inject
	private CompanyService companyService;
	@Inject
	private AgencyService agencyService;
	@Inject
	private LoginService loginService;
	@Inject
	private SalesOrderItemService soItemService;

	private String customerName ;

	private Long salesOrderId;
	
	private String invoiceDate;

	private boolean isProformat=false;

	public SalesOrderPrinterDataService setSalesOrderId(Long salesOrderId)
	{
		this.salesOrderId = salesOrderId;
		return this;
	}
	public String getCustomerName(){
		return customerName ;
	}
	public SalesOrderPrinterDataService setCustomerName(String customerName)
	{
		this.customerName = customerName;
		return this;
	}

	public SalesOrderPrinterDataService setIsProforma(boolean isProformat)
	{
		this.isProformat = isProformat;
		return this;
	}
	
	public String getInvoiceDate() {
		return invoiceDate;
	}
	
	public SalesOrderPrinterDataService setInvoiceDate(String invoiceDate)
	{
		this.invoiceDate = invoiceDate;
		return this;
	}
	

	@Override
	protected Task<SalesOrderPrinterData> createTask()
	{
		return new Task<SalesOrderPrinterData>()
				{
			@Override
			protected SalesOrderPrinterData call() throws Exception
			{
				if (salesOrderId == null)
					return null;
				SalesOrderPrinterData result = null;
				SalesOrder salesOrder = salesOrderService.findById(salesOrderId);
				Agency agency = agencyService.findById(salesOrder.getAgency().getId());
				Company company = companyService.findById(agency.getCompany().getId());
				Login login = loginService.findById(salesOrder.getSalesAgent().getId());
				SalesOrderItem soItem = new SalesOrderItem();
				soItem.setSalesOrder(new SalesOrderItemSalesOrder(salesOrder));
				SalesOrderItemSearchInput searchInput = new SalesOrderItemSearchInput();
				searchInput.setEntity(soItem);
				searchInput.getFieldNames().add("salesOrder");
				SalesOrderItemSearchResult found = soItemService.findBy(searchInput);
				result = new SalesOrderPrinterData(salesOrder, company, agency, login, found,isProformat);
				return result;
			}
				};
	}
}
