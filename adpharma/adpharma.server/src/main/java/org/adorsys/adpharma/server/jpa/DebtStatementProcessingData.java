package org.adorsys.adpharma.server.jpa;

import java.util.Date;

public class DebtStatementProcessingData {
	
	private Date fromDate ;
	
	private Date toDate ;
	
	private Date expectedPayementDate ;
	
	private Customer customer ;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getExpectedPayementDate() {
		return expectedPayementDate;
	}

	public void setExpectedPayementDate(Date expectedPayementDate) {
		this.expectedPayementDate = expectedPayementDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "DebtStatementProcessingData [customer=" + customer + "]";
	}
	
	

}
