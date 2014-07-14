package org.adorsys.adpharma.client.events;

public class PaymentId extends DomainObjectId {

	private String customerName ;
	
	public PaymentId(Long id) {
		super(id);
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
