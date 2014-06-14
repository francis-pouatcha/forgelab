package org.adorsys.adpharma.server.jpa;

import java.util.Date;

public class PeriodicalDeliveryDataSearchInput {

	 private Date beginDate;
	 
	 private Date endDate;
	 
	 private Supplier supplier;
	 
	 private Boolean check;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}
	 
	
}
