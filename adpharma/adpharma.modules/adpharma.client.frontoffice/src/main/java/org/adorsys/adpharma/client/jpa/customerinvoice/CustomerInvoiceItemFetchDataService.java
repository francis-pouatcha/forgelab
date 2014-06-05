package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemService;

public class CustomerInvoiceItemFetchDataService extends
		Service<CustomerInvoicePrinterData> {
	@Inject
	private CustomerInvoiceItemService customerInvoiceItemService;

	private CustomerInvoicePrinterData customerInvoicePrinterData;
	
	private CustomerInvoicePrintTemplate customerInvoicePrintTemplateWorker;

	private ReceiptPrintTemplate receiptPrintTemplateWorker;
	
	public CustomerInvoicePrintTemplate getCustomerInvoicePrintTemplateWorker() {
		return customerInvoicePrintTemplateWorker;
	}

	public CustomerInvoiceItemFetchDataService setCustomerInvoicePrintTemplateWorker(
			CustomerInvoicePrintTemplate customerInvoicePrintTemplateWorker) {
		this.customerInvoicePrintTemplateWorker = customerInvoicePrintTemplateWorker;
		return this;
	}

	public CustomerInvoicePrinterData getCustomerInvoicePrinterData() {
		return customerInvoicePrinterData;
	}

	public CustomerInvoiceItemFetchDataService setCustomerInvoicePrinterData(
			CustomerInvoicePrinterData customerInvoicePrinterData) {
		this.customerInvoicePrinterData = customerInvoicePrinterData;
		return this;
	}
	
	

	public CustomerInvoiceItemFetchDataService setReceiptPrintTemplateWorker(
			ReceiptPrintTemplate worker) {
		this.receiptPrintTemplateWorker = worker;
		return this;
	}
	
	public ReceiptPrintTemplate getReceiptPrintTemplateWorker() {
		return receiptPrintTemplateWorker;
	}

	@Override
	protected Task<CustomerInvoicePrinterData> createTask() {
		return new Task<CustomerInvoicePrinterData>() {
			@Override
			protected CustomerInvoicePrinterData call() throws Exception {
				
				CustomerInvoiceItemSearchResult customerInvoiceItemSearchResult = customerInvoicePrinterData.getCustomerInvoiceItemSearchResult();
				CustomerInvoiceItemSearchInput itemSearchInput = customerInvoiceItemSearchResult.getSearchInput();
				int start = itemSearchInput.getStart();
				int max = itemSearchInput.getMax();
				start +=max;
				itemSearchInput.setStart(start);
				
				if(start>customerInvoiceItemSearchResult.getCount()) {
					List<CustomerInvoiceItem> resultList = Collections.emptyList();
					customerInvoiceItemSearchResult = new CustomerInvoiceItemSearchResult(customerInvoiceItemSearchResult.getCount(), resultList, itemSearchInput);
				} else {
					customerInvoiceItemSearchResult = customerInvoiceItemService.findBy(itemSearchInput);
					
				}
				customerInvoicePrinterData.setCustomerInvoiceItemSearchResult(customerInvoiceItemSearchResult);
				return customerInvoicePrinterData;
			}
		};
	}
}
