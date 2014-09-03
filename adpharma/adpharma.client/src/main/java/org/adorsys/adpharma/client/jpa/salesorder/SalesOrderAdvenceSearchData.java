package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Date;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;

public class SalesOrderAdvenceSearchData {

	private Date fromDate ;
	
	private Date toDate ;
	
	private Login saller ;
	
	private String articleName ;
	
	private Boolean onlyCrediSales = Boolean.FALSE ;
	
	public Boolean getOnlyCrediSales() {
		return onlyCrediSales;
	}

	public void setOnlyCrediSales(Boolean onlyCrediSales) {
		this.onlyCrediSales = onlyCrediSales;
	}

	private DocumentProcessingState sate ;

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

	public Login getSaller() {
		return saller;
	}

	public void setSaller(Login saller) {
		this.saller = saller;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public DocumentProcessingState getSate() {
		return sate;
	}

	public void setSate(DocumentProcessingState sate) {
		this.sate = sate;
	}

	@Override
	public String toString() {
		return "SalesOrderAdvenceSearchData [fromDate=" + fromDate
				+ ", toDate=" + toDate + ", saller=" + saller
				+ ", articleName=" + articleName + "]";
	}
	
	
	
}
