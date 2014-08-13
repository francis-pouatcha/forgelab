package org.adorsys.adpharma.server.jpa;

import java.util.Date;

public class InvoiceByAgencyPrintInput {
	private Date fromDate;

	private Date toDate;
	
	private Boolean onlyInsurrer ;

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Boolean getOnlyInsurrer() {
		return onlyInsurrer;
	}

	public void setOnlyInsurrer(Boolean onlyInsurrer) {
		this.onlyInsurrer = onlyInsurrer;
	}

	
	
	

}
