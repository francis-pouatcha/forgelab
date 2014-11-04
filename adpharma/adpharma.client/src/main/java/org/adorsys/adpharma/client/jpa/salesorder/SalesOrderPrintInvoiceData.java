package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Calendar;

import org.adorsys.adpharma.client.events.SalesOrderId;

import javafx.beans.property.SimpleObjectProperty;

public class SalesOrderPrintInvoiceData {
	
private SimpleObjectProperty<Calendar> invoiceDate;

private SimpleObjectProperty<String> customerName;

private SalesOrderId salesOrderId;


public SimpleObjectProperty<Calendar> invoiceDateProperty()
{
   if (invoiceDate == null)
   {
 	  invoiceDate = new SimpleObjectProperty<Calendar>();
   }
   return invoiceDate;
}

public Calendar getInvoiceDate()
{
   return invoiceDateProperty().get();
}

public final void setInvoiceDate(Calendar invoiceDate)
{
   this.invoiceDateProperty().set(invoiceDate);
}


public SimpleObjectProperty<String> customerNameProperty()
{
   if (customerName == null)
   {
	   customerName = new SimpleObjectProperty<String>();
   }
   return customerName;
}

public String getCustomerName()
{
   return customerNameProperty().get();
}

public final void setCustomerName(String customerName)
{
   this.customerNameProperty().set(customerName);
}

public SalesOrderId getSalesOrderId() {
	return salesOrderId;
}

public void setSalesOrderId(SalesOrderId salesOrderId) {
	this.salesOrderId = salesOrderId;
}


}
