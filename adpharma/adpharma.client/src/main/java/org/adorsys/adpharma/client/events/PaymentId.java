package org.adorsys.adpharma.client.events;

public class PaymentId extends DomainObjectId {

	private String customerName ;
	
	private boolean printWhithouthDiscount = false ;
	
	public PaymentId(Long id) {
		super(id);
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public boolean isPrintWhithoutDiscount() {
		return printWhithouthDiscount;
	}
	public void setPrintWhithoutDiscount(boolean printWhithouthDiscount) {
		this.printWhithouthDiscount = printWhithouthDiscount;
	}
	
	

}
