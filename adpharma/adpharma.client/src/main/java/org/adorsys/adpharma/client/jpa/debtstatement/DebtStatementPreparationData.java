package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;

import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerMode;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

public class DebtStatementPreparationData {

	@Description("ProcurementOrder_procmtOrderTriggerMode_description")
	private SimpleObjectProperty<ProcmtOrderTriggerMode> procmtOrderTriggerMode;

	@Description("DebtStatement_insurrance_description")
	private SimpleObjectProperty<Customer> insurrance;

	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private SimpleObjectProperty<Calendar> fromDate;

	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private SimpleObjectProperty<Calendar> toDate;

	 public SimpleObjectProperty<Customer> insurranceProperty()
	   {
	      if (insurrance == null)
	      {
	    	  insurrance = new SimpleObjectProperty<Customer>(new Customer());
	      }
	      return insurrance;
	   }

	   @NotNull
	   public Customer getInsurrance()
	   {
	      return insurranceProperty().get();
	   }

	   public final void setInsurrance(Customer insurrance)
	   {
	      if (insurrance == null)
	      {
	    	  insurrance = new Customer();
	      }
	      PropertyReader.copy(insurrance, getInsurrance());
	      insurranceProperty().setValue(ObjectUtils.clone(getInsurrance()));
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
