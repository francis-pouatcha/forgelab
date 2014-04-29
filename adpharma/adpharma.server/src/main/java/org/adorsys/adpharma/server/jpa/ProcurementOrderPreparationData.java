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



}
