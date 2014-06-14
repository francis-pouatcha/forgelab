package org.adorsys.adpharma.server.jpa;

import java.util.Date;


public class DeliveryStattisticsDataSearchInput {
	private Integer years ;

	private Supplier supplier ;
	
	private Date fromDate ;
	
	private Date toDate ;
	
	private Boolean groupByCip = Boolean.FALSE ;

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Supplier getDeliverySupplier() {
		return supplier;
	}

	public void setDeliverySupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

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

	public Boolean getGroupByCip() {
		return groupByCip;
	}

	public void setGroupByCip(Boolean groupByCip) {
		this.groupByCip = groupByCip;
	}


}
