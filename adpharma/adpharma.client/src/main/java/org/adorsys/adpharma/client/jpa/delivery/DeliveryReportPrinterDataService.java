package org.adorsys.adpharma.client.jpa.delivery;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginService;

public class DeliveryReportPrinterDataService extends
		Service<DeliveryReportPrinterData> {

	@Inject
	private DeliveryService deliveryService;
	@Inject
	private LoginService loginService;
	@Inject
	private DeliveryItemService deliveryItemService;

	private Delivery delivery;

	public DeliveryReportPrinterDataService setDelivery(Delivery delivery) {
		this.delivery = delivery;
		return this;
	}

	@Override
	protected Task<DeliveryReportPrinterData> createTask() {
		return new Task<DeliveryReportPrinterData>() {
			@Override
			protected DeliveryReportPrinterData call() throws Exception {
				if (delivery == null || delivery.getId()==null)
					return null;
				DeliveryReportPrinterData result = null;
				delivery = deliveryService.findById(delivery.getId());
				Login login = loginService.findById(delivery.getCreatingUser().getId());
				DeliveryItem deliveryItem = new DeliveryItem();
				deliveryItem.setDelivery(new DeliveryItemDelivery(delivery));
				DeliveryItemSearchInput deliveryItemSearchInput = new DeliveryItemSearchInput();
				deliveryItemSearchInput.setEntity(deliveryItem);
				deliveryItemSearchInput.getFieldNames().add("delivery");
				DeliveryItemSearchResult deliveryItemSearchResult = deliveryItemService
						.findBy(deliveryItemSearchInput);
				result = new DeliveryReportPrinterData(delivery, login,
						deliveryItemSearchResult);
				return result;
			}
		};
	}
}
