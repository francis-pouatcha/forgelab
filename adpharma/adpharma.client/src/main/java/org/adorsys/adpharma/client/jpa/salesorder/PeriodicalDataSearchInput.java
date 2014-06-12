package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Calendar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.validation.constraints.NotNull;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;

public class PeriodicalDataSearchInput {

	private SimpleObjectProperty<Calendar> beginDate;

	private SimpleObjectProperty<Calendar> endDate;

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

	public  void setBeginDate(Calendar beginDate)
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

	public  void setEndDate(Calendar endDate)
	{
		this.endDateProperty().set(endDate);
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
