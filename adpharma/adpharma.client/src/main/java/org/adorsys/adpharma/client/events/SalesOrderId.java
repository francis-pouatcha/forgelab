package org.adorsys.adpharma.client.events;

/**
 * The identifier of a sales order.
 * 
 * @author francis
 *
 */
public class SalesOrderId extends DomainObjectId {
	
	private String customerName ;
	public SalesOrderId(Long id) {
		super(id);
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
