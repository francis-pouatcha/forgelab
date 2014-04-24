package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerMode;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

public class ProcurementOrderPreparationData {

	@Description("ProcurementOrder_procmtOrderTriggerMode_description")
	private SimpleObjectProperty<ProcmtOrderTriggerMode> procmtOrderTriggerMode;

	@Description("ProcurementOrder_supplier_description")
	private SimpleObjectProperty<Supplier> supplier;

	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private SimpleObjectProperty<Calendar> fromDate;

	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private SimpleObjectProperty<Calendar> toDate;

	 public SimpleObjectProperty<Supplier> supplierProperty()
	   {
	      if (supplier == null)
	      {
	         supplier = new SimpleObjectProperty<Supplier>(new Supplier());
	      }
	      return supplier;
	   }

	   @NotNull(message = "ProcurementOrder_supplier_NotNull_validation")
	   public Supplier getSupplier()
	   {
	      return supplierProperty().get();
	   }

	   public final void setSupplier(ProcurementOrderSupplier supplier)
	   {
	      if (supplier == null)
	      {
	         supplier = new ProcurementOrderSupplier();
	      }
	      PropertyReader.copy(supplier, getSupplier());
	      supplierProperty().setValue(ObjectUtils.clone(getSupplier()));
	   }
	   
	public SimpleObjectProperty<Calendar> toDateProperty()
	{
		if (toDate == null)
		{
			toDate = new SimpleObjectProperty<Calendar>();
		}
		return toDate;
	}

	public Calendar getToDate()
	{
		return toDateProperty().get();
	}

	public final void setToDate(Calendar toDate)
	{
		this.toDateProperty().set(toDate);
	}

	public SimpleObjectProperty<Calendar> fromDateProperty()
	{
		if (fromDate == null)
		{
			fromDate = new SimpleObjectProperty<Calendar>();
		}
		return fromDate;
	}

	public Calendar getFromDate()
	{
		return fromDateProperty().get();
	}

	public final void setFromDate(Calendar fromDate)
	{
		this.fromDateProperty().set(fromDate);
	}



	public SimpleObjectProperty<ProcmtOrderTriggerMode> procmtOrderTriggerModeProperty()
	{
		if (procmtOrderTriggerMode == null)
		{
			procmtOrderTriggerMode = new SimpleObjectProperty<ProcmtOrderTriggerMode>();
		}
		return procmtOrderTriggerMode;
	}

	public ProcmtOrderTriggerMode getProcmtOrderTriggerMode()
	{
		return procmtOrderTriggerModeProperty().get();
	}

	public final void setProcmtOrderTriggerMode(ProcmtOrderTriggerMode procmtOrderTriggerMode)
	{
		this.procmtOrderTriggerModeProperty().set(procmtOrderTriggerMode);
	}
}
