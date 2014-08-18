package org.adorsys.adpharma.server.jpa;

import java.util.Date;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

public class ProcurementOrderPreparationData {

	@Description("ProcurementOrder_procmtOrderTriggerMode_description")
	private ProcmtOrderTriggerMode procmtOrderTriggerMode;

	@Description("ProcurementOrder_supplier_description")
	private Supplier supplier;

	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date fromDate;

	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date toDate;
	
	private Login creatingUser;
	
	private DocumentProcessingState poStatus;
	
	private String procurementOrderNumber;

	public ProcmtOrderTriggerMode getProcmtOrderTriggerMode() {
		return procmtOrderTriggerMode;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setProcmtOrderTriggerMode(
			ProcmtOrderTriggerMode procmtOrderTriggerMode) {
		this.procmtOrderTriggerMode = procmtOrderTriggerMode;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Login getCreatingUser() {
		return creatingUser;
	}

	public void setCreatingUser(Login creatingUser) {
		this.creatingUser = creatingUser;
	}

	public DocumentProcessingState getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(DocumentProcessingState poStatus) {
		this.poStatus = poStatus;
	}

	public String getProcurementOrderNumber() {
		return procurementOrderNumber;
	}

	public void setProcurementOrderNumber(String procurementOrderNumber) {
		this.procurementOrderNumber = procurementOrderNumber;
	}



}
