package org.adorsys.adpharma.client.utils;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

public class DeliveryFromOrderData {

	private Delivery  delivery ;
	
	private ProcurementOrder order ;

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public ProcurementOrder getOrder() {
		return order;
	}

	public void setOrder(ProcurementOrder order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "DeliveryFromOrderData [delivery=" + delivery + ", order="
				+ order + "]";
	}
	
	public DeliveryFromOrderData() {
		// TODO Auto-generated constructor stub
	}

	public DeliveryFromOrderData(Delivery delivery, ProcurementOrder order) {
		super();
		this.delivery = delivery;
		this.order = order;
	}
	
}
