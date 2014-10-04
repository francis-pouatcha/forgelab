package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.Calendar;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import javafx.beans.property.SimpleObjectProperty;

public class PeriodicalPrescriptionBookDataSearchInput {
	
	private SimpleObjectProperty<Calendar> beginDate;
	 
	private SimpleObjectProperty<Calendar> endDate;
	 
	public SimpleObjectProperty<Calendar> beginDateProperty()
	   {
	      if (beginDate == null)
	      {
	    	  beginDate = new SimpleObjectProperty<Calendar>();
	      }
	      return beginDate;
	   }

	   public Calendar getBeginDate()
	   {
	      return beginDateProperty().get();
	   }

	   public final void setBeginDate(Calendar beginDate)
	   {
	      this.beginDateProperty().set(beginDate);
	   }
	   
	   public SimpleObjectProperty<Calendar> endDateProperty()
	   {
	      if (endDate == null)
	      {
	    	  endDate = new SimpleObjectProperty<Calendar>();
	      }
	      return endDate;
	   }

	   public Calendar getEndDate()
	   {
	      return endDateProperty().get();
	   }

	   public final void setEndDate(Calendar endDate)
	   {
	      this.endDateProperty().set(endDate);
	   }	
	
	   public String toString()
	   {
	      return PropertyReader.buildToString(this, "beginDate","endDate");
	   }

}
