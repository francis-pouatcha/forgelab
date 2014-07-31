package org.adorsys.adpharma.server.jpa;

import java.util.Date;

public class InventoryAdvenceSearchData {

	private Date fromDate ;
	
	private Date toDate ;
	
	private Login agent ;
	
	private String articleName ;
	
	private DocumentProcessingState sate ;
	
	private Section section ;

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

	public Login getAgent() {
		return agent;
	}

	public void setAgent(Login agent) {
		this.agent = agent;
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

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return articleName ;
	}
	
	
	
}
