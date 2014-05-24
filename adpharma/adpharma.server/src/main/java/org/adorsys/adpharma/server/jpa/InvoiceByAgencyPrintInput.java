package org.adorsys.adpharma.server.jpa;

import java.util.Date;

public class InvoiceByAgencyPrintInput {
	private Date fromDate;

	private Date toDate;

	private Agency agency;

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	
	
	

}
