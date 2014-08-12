package org.adorsys.adpharma.server.utils;

import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PeriodicalDataSearchInput {

	 private Date beginDate;
	 
	 private Date endDate;
	 
	 private Boolean check;
	 
	 private Boolean taxableSalesOnly = Boolean.FALSE ;
	 
	 private Boolean nonTaxableSalesOnly = Boolean.FALSE ;
	 
	 private Boolean twentyOverHeightySalesOnly = Boolean.FALSE ;
	 
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

	public Boolean getCheck() {
		return check;
	}
	
	public Boolean getTaxableSalesOnly() {
		return taxableSalesOnly;
	}

	public Boolean getNonTaxableSalesOnly() {
		return nonTaxableSalesOnly;
	}


	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Boolean getTwentyOverHeightySalesOnly() {
		return twentyOverHeightySalesOnly;
	}

	public void setTwentyOverHeightySalesOnly(Boolean twentyOverHeightySalesOnly) {
		this.twentyOverHeightySalesOnly = twentyOverHeightySalesOnly;
	}

	public void setTaxableSalesOnly(Boolean taxableSalesOnly) {
		this.taxableSalesOnly = taxableSalesOnly;
	}

	public void setNonTaxableSalesOnly(Boolean nonTaxableSalesOnly) {
		this.nonTaxableSalesOnly = nonTaxableSalesOnly;
	}
	


}
