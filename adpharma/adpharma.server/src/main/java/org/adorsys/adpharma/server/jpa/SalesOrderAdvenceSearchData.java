package org.adorsys.adpharma.server.jpa;

import java.util.Date;

public class SalesOrderAdvenceSearchData {

	private Date fromDate ;
	
	private Date toDate ;
	
	private Login saller ;
	
	private String articleName ;
	
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
