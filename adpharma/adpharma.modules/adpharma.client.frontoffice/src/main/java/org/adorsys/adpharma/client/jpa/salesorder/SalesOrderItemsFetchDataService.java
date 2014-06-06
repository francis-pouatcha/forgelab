package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customerinvoice.ReceiptPrintTemplate;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemService;

public class SalesOrderItemsFetchDataService extends
		Service<SalesOrderPrinterData> {
	@Inject
	private SalesOrderItemService salesOrderItemService;

	private SalesOrderPrinterData salesOrderPrinterData;
	
	private SalesOrderPrintTemplate salesOrderPrintTemplateWorker;

	private ReceiptPrintTemplate receiptPrintTemplateWorker;
	
	public SalesOrderPrintTemplate getSalesOrderPrintTemplateWorker() {
		return salesOrderPrintTemplateWorker;
	}

	public SalesOrderItemsFetchDataService setSalesOrderPrintTemplateWorker(
			SalesOrderPrintTemplate salesOrderPrintTemplateWorker) {
		this.salesOrderPrintTemplateWorker = salesOrderPrintTemplateWorker;
		return this;
	}

	public SalesOrderPrinterData getSalesOrderPrinterData() {
		return salesOrderPrinterData;
	}

	public SalesOrderItemsFetchDataService setSalesOrderPrinterData(
			SalesOrderPrinterData salesOrderPrinterData) {
		this.salesOrderPrinterData = salesOrderPrinterData;
		return this;
	}
	
	

	public SalesOrderItemsFetchDataService setReceiptPrintTemplateWorker(
			ReceiptPrintTemplate worker) {
		this.receiptPrintTemplateWorker = worker;
		return this;
	}
	
	public ReceiptPrintTemplate getReceiptPrintTemplateWorker() {
		return receiptPrintTemplateWorker;
	}

	@Override
	protected Task<SalesOrderPrinterData> createTask() {
		return new Task<SalesOrderPrinterData>() {
			@Override
			protected SalesOrderPrinterData call() throws Exception {
				
				SalesOrderItemSearchResult salesOrderItemSearchResult = salesOrderPrinterData.getSalesOrderItemSearchResult();
				SalesOrderItemSearchInput itemSearchInput = salesOrderItemSearchResult.getSearchInput();
				int start = itemSearchInput.getStart();
				int max = itemSearchInput.getMax();
				start +=max;
				itemSearchInput.setStart(start);
				
				if(start>salesOrderItemSearchResult.getCount()) {
					List<SalesOrderItem> resultList = Collections.emptyList();
					salesOrderItemSearchResult = new SalesOrderItemSearchResult(salesOrderItemSearchResult.getCount(), resultList, itemSearchInput);
				} else {
					salesOrderItemSearchResult = salesOrderItemService.findBy(itemSearchInput);
					
				}
				salesOrderPrinterData.setSalesOrderItemSearchResult(salesOrderItemSearchResult);
				return salesOrderPrinterData;
			}
		};
	}
}
