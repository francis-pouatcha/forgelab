package org.adorsys.adpharma.client.jpa.inventory;

import java.util.Date;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;

import com.itextpdf.text.Section;

public class InventoryAdvancedSearchData {
	
	private Date fromDate;
	
	private Date toDate;
	
	private String inventoryNumber;
	
	private DocumentProcessingState inventoryStatus;
	
	private Login user;
	
	private Section section;

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

	public String getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public DocumentProcessingState getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(DocumentProcessingState inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public Login getUser() {
		return user;
	}

	public void setUser(Login user) {
		this.user = user;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
	
	

	
	
	

}
