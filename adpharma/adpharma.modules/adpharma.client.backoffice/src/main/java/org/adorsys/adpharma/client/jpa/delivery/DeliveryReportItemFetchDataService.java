package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemService;

public class DeliveryReportItemFetchDataService extends
		Service<DeliveryReportPrinterData> {
	@Inject
	private DeliveryItemService deliveryItemService;

	private DeliveryReportPrinterData deliveryReportPrinterData;
	
	private DeliveryReportPrintTemplate deliveryReportPrintTemplateWorker;

	public DeliveryReportItemFetchDataService setDeliveryReportPrinterData(
			DeliveryReportPrinterData deliveryReportPrinterData) {
		this.deliveryReportPrinterData = deliveryReportPrinterData;
		return this;
	}

	public DeliveryReportItemFetchDataService setDeliveryReportPrintTemplateWorker(
			DeliveryReportPrintTemplate deliveryReportPrintTemplateWorker) {
		this.deliveryReportPrintTemplateWorker = deliveryReportPrintTemplateWorker;
		return this;
	}

	public DeliveryReportPrinterData getDeliveryReportPrinterData() {
		return deliveryReportPrinterData;
	}

	public DeliveryReportPrintTemplate getDeliveryReportPrintTemplateWorker() {
		return deliveryReportPrintTemplateWorker;
	}

	@Override
	protected Task<DeliveryReportPrinterData> createTask() {
		return new Task<DeliveryReportPrinterData>() {
			@Override
			protected DeliveryReportPrinterData call() throws Exception {
				DeliveryItemSearchResult deliveryItemSearchResult = deliveryReportPrinterData.getDeliveryItemSearchResult();
				DeliveryItemSearchInput itemSearchInput = deliveryItemSearchResult.getSearchInput();
				int start = itemSearchInput.getStart();
				int max = itemSearchInput.getMax();
				start +=max;
				itemSearchInput.setStart(start);
				
				if(start>deliveryItemSearchResult.getCount()) {
					List<DeliveryItem> resultList = Collections.emptyList();
					deliveryItemSearchResult = new DeliveryItemSearchResult(deliveryItemSearchResult.getCount(), resultList, itemSearchInput);
				} else {
					deliveryItemSearchResult = deliveryItemService.findBy(itemSearchInput);
					
				}
				deliveryReportPrinterData.setDeliveryItemSearchResult(deliveryItemSearchResult);
				return deliveryReportPrinterData;
			}
		};
	}
}
