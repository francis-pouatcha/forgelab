package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemService;

public class ProcurementOrderReportItemFetchDataService extends
Service<ProcurementOrderReportPrinterData> {
	@Inject
	private ProcurementOrderItemService poItemService;

	private ProcurementOrderReportPrinterData poReportPrinterData;

	private ProcurementOrderReportPrintTemplate poReportPrintTemplateWorker;

	public ProcurementOrderReportItemFetchDataService setProcurementOrderReportPrinterData(
			ProcurementOrderReportPrinterData deliveryReportPrinterData) {
		this.poReportPrinterData = deliveryReportPrinterData;
		return this;
	}

	public ProcurementOrderReportItemFetchDataService setProcurementOrderReportPrintTemplateWorker(
			ProcurementOrderReportPrintTemplate deliveryReportPrintTemplateWorker) {
		this.poReportPrintTemplateWorker = deliveryReportPrintTemplateWorker;
		return this;
	}

	public ProcurementOrderReportPrinterData getProcurementOrderReportPrinterData() {
		return poReportPrinterData;
	}

	public ProcurementOrderReportPrintTemplate getProcurementOrderReportPrintTemplateWorker() {
		return poReportPrintTemplateWorker;
	}

	@Override
	protected Task<ProcurementOrderReportPrinterData> createTask() {
		return new Task<ProcurementOrderReportPrinterData>() {
			@Override
			protected ProcurementOrderReportPrinterData call() throws Exception {
				ProcurementOrderItemSearchResult deliveryItemSearchResult = poReportPrinterData.getProcurementOrderItemSearchResult();
				ProcurementOrderItemSearchInput itemSearchInput = deliveryItemSearchResult.getSearchInput();
				int start = itemSearchInput.getStart();
				int max = itemSearchInput.getMax();
				start +=max;
				itemSearchInput.setStart(start);

				if(start>deliveryItemSearchResult.getCount()) {
					List<ProcurementOrderItem> resultList = Collections.emptyList();
					deliveryItemSearchResult = new ProcurementOrderItemSearchResult(deliveryItemSearchResult.getCount(), resultList, itemSearchInput);
				} else {
					deliveryItemSearchResult = poItemService.findBy(itemSearchInput);

				}
				poReportPrinterData.setProcurementOrderItemSearchResult(deliveryItemSearchResult);
				return poReportPrinterData;
			}
		};

	}
}