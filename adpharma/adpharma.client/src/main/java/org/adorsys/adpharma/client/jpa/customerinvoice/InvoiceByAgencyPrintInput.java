package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

import javafx.beans.property.SimpleObjectProperty;

public class InvoiceByAgencyPrintInput {
	private SimpleObjectProperty<Calendar> fromDate;

	private SimpleObjectProperty<Calendar> toDate;

	private SimpleObjectProperty<Agency> agency;
	
	 public SimpleObjectProperty<Calendar> toDateProperty()
	   {
	      if (toDate == null)
	      {
	    	  toDate = new SimpleObjectProperty<Calendar>();
	      }
	      return toDate;
	   }

	 @NotNull
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

	@NotNull
	public Calendar getFromDate()
	{
		return fromDateProperty().get();
	}

	public final void setFromDate(Calendar fromDate)
	{
		this.fromDateProperty().set(fromDate);
	}	   

	public SimpleObjectProperty<Agency> agencyProperty()
	{
		if (agency == null)
		{
			agency = new SimpleObjectProperty<Agency>(new Agency());
		}
		return agency;
	}

	@NotNull(message = "CustomerInvoice_agency_NotNull_validation")
	public Agency getAgency()
	{
		return agencyProperty().get();
	}

	public final void setAgency(Agency agency)
	{
		if (agency == null)
		{
			agency = new Agency();
		}
		PropertyReader.copy(agency, getAgency());
		agencyProperty().setValue(ObjectUtils.clone(getAgency()));
	}

}
