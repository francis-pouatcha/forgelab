package org.adorsys.adpharma.client.jpa.delivery;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.login.Login;

public class DeliveryReportPrinterData {

	private final Delivery delivery;
	private final Login login;
	private DeliveryItemSearchResult deliveryItemSearchResult;

	public DeliveryReportPrinterData(Delivery delivery,
			Login login, DeliveryItemSearchResult deliveryItemSearchResult) {
		super();
		this.delivery = delivery;
		this.login = login;
		this.deliveryItemSearchResult = deliveryItemSearchResult;
	}

	public Login getLogin() {
		return login;
	}
	public Delivery getDelivery(){
		return delivery;
	}

	public DeliveryItemSearchResult getDeliveryItemSearchResult() {
		return deliveryItemSearchResult;
	}

	public void setDeliveryItemSearchResult(
			DeliveryItemSearchResult deliveryItemSearchResult) {
		this.deliveryItemSearchResult = deliveryItemSearchResult;
	}

}
