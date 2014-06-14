package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Calendar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

public class PeriodicalDeliveryDataSearchInput {

	 private SimpleObjectProperty<Calendar> beginDate;
	 
	 private SimpleObjectProperty<Calendar> endDate;
	 
	 private SimpleObjectProperty<Supplier> supplier;
	 
	 private SimpleBooleanProperty check;
	 
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
	   
	   public SimpleObjectProperty<Supplier> supplierProperty()
	   {
	      if (supplier == null)
	      {
	    	  supplier = new SimpleObjectProperty<Supplier>();
	      }
	      return supplier;
	   }

	   public Supplier getSupplier()
	   {
	      return supplierProperty().get();
	   }

	   public final void setSupplier(Supplier supplier)
	   {
	      this.supplierProperty().set(supplier);
	   }
	   
	   public SimpleBooleanProperty checkProperty()
		{
			if (check == null)
			{
				check = new SimpleBooleanProperty();
			}
			return check;
		}

		public Boolean getCheck()
		{
			return checkProperty().get();
		}

		public final void setCheck(Boolean check)
		{
			if (check == null)
				check = Boolean.FALSE;
			this.checkProperty().set(check);
		}

	   public String toString()
	   {
	      return PropertyReader.buildToString(this, "beginDate","endDate");
	   }

}
