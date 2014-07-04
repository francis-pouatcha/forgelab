package org.adorsys.adpharma.server.utils;

import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.ProcurementOrder;

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
