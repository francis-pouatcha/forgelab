package org.adorsys.adpharma.client.events;

import java.util.Date;

/**
 * The identifier of a sales order.
 * 
 * @author francis
 *
 */
public class SalesOrderId extends DomainObjectId {
	
	private String customerName ;
	
	private boolean isProformat = false ;
	
	private String invoiceDate;
	
	public SalesOrderId(Long id) {
		super(id);
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public boolean isProformat() {
		return isProformat;
	}
	
	public void setProformat(boolean isProformat) {
		this.isProformat = isProformat;
	}
	
	public String getInvoiceDate() {
		return invoiceDate;
	}
	
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
	
}
