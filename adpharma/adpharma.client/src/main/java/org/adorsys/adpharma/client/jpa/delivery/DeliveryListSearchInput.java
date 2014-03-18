package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Calendar;
import java.util.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

public class DeliveryListSearchInput {

	private SimpleStringProperty deliveryNumber;

	private SimpleObjectProperty<Calendar> deliveryDateFrom ;

	private SimpleObjectProperty<Calendar> deliveryDateTo ;

	private SimpleObjectProperty<Supplier> supplier;

	private SimpleObjectProperty<DocumentProcessingState> deliveryProcessingState;

	public SimpleObjectProperty<DocumentProcessingState> deliveryProcessingStateProperty()
	{
		if (deliveryProcessingState == null)
		{
			deliveryProcessingState = new SimpleObjectProperty<DocumentProcessingState>();
		}
		return deliveryProcessingState;
	}

	public SimpleObjectProperty<Supplier> supplierProperty()
	{
		if (supplier == null)
		{
			supplier = new SimpleObjectProperty<Supplier>();
		}
		return supplier;
	}

	public SimpleObjectProperty<Calendar> deliveryDateFromProperty()
	{
		if (deliveryDateFrom == null)
		{
			deliveryDateFrom = new SimpleObjectProperty<Calendar>();
		}
		return deliveryDateFrom;
	}
	public SimpleObjectProperty<Calendar> deliveryDateToProperty()
	{
		if (deliveryDateTo == null)
		{
			deliveryDateTo = new SimpleObjectProperty<Calendar>();
		}
		return deliveryDateTo;
	}

	public SimpleStringProperty deliveryNumberProperty()
	{
		if (deliveryNumber == null)
		{
			deliveryNumber = new SimpleStringProperty();
		}
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber.set(deliveryNumber);;
	}

	public void setDeliveryDateFrom(Calendar deliveryDateFrom) {
		this.deliveryDateFrom.set(deliveryDateFrom);
	}

	public void setDeliveryDateTo(Calendar deliveryDateTo) {
		this.deliveryDateTo.set(deliveryDateTo);
	}

	public void setSupplier(Supplier supplier) {
		this.supplier.set(supplier);
	}

	public void setDeliveryProcessingState(
			DocumentProcessingState deliveryProcessingState) {
		this.deliveryProcessingState.set(deliveryProcessingState);
	}

	public String getDeliveryNumber() {
		return deliveryNumber.get();
	}

	public Calendar getDeliveryDateFrom() {
		return deliveryDateFrom.get();
	}

	public Calendar getDeliveryDateTo() {
		return deliveryDateTo.get();
	}

	public Supplier getSupplier() {
		return supplier.get();
	}

	public DocumentProcessingState getDeliveryProcessingState() {
		return deliveryProcessingState.get();
	}
	
	public String toString()
	   {
	      return PropertyReader.buildToString(this, "deliveryNumber","deliveryDateFrom","deliveryDateTo","supplier");
	   }
}
