package org.adorsys.adpharma.server.jpa;

import java.util.Date;

public class DeliveryListSearchInput {
	
	 private String deliveryNumber;
	 
	 private Date deliveryDateFrom ;
	 
	 private Date deliveryDateTo ;
	 
	 private Supplier supplier;
	 
	 private DocumentProcessingState deliveryProcessingState;

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public void setDeliveryDateFrom(Date deliveryDateFrom) {
		this.deliveryDateFrom = deliveryDateFrom;
	}

	public void setDeliveryDateTo(Date deliveryDateTo) {
		this.deliveryDateTo = deliveryDateTo;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public void setDeliveryProcessingState(
			DocumentProcessingState deliveryProcessingState) {
		this.deliveryProcessingState = deliveryProcessingState;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public Date getDeliveryDateFrom() {
		return deliveryDateFrom;
	}

	public Date getDeliveryDateTo() {
		return deliveryDateTo;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public DocumentProcessingState getDeliveryProcessingState() {
		return deliveryProcessingState;
	}
	 
	 
	 

}
