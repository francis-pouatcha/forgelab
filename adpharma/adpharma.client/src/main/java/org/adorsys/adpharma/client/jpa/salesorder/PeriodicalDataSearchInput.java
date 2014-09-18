package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Calendar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.validation.constraints.NotNull;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;

public class PeriodicalDataSearchInput {

	private SimpleObjectProperty<Calendar> beginDate;

	private SimpleObjectProperty<Calendar> endDate;

	private SimpleBooleanProperty check;


	private SimpleBooleanProperty taxableSalesOnly ;

	private SimpleBooleanProperty nonTaxableSalesOnly  ;

	private SimpleBooleanProperty twentyOverHeightySalesOnly  ;
	
	private SimpleStringProperty pic;
	
	private SimpleStringProperty articleName ; 
	
	private SimpleStringProperty internalPic ;  

	 
	   
	   public SimpleStringProperty internalPicProperty()
	   {
	      if (internalPic == null)
	      {
	    	  internalPic = new SimpleStringProperty();
	      }
	      return internalPic;
	   }

	   public String getInternalPic()
	   {
	      return internalPicProperty().get();
	   }

	   public final void setInternalPic(String internalPic)
	   {
	      this.internalPicProperty().set(internalPic);
	   }
	   
	   public SimpleStringProperty articleNameProperty()
	   {
	      if (articleName == null)
	      {
	    	  articleName = new SimpleStringProperty();
	      }
	      return articleName;
	   }

	   public String getArticleName()
	   {
	      return articleNameProperty().get();
	   }

	   public final void setArticleName(String articleName)
	   {
	      this.articleNameProperty().set(articleName);
	   }

	public SimpleObjectProperty<Calendar> beginDateProperty()
	{
		if (beginDate == null)
		{
			beginDate = new SimpleObjectProperty<Calendar>();
		}
		return beginDate;
	}
	public SimpleStringProperty picProperty()
	   {
	      if (pic == null)
	      {
	    	  pic = new SimpleStringProperty();
	      }
	      return pic;
	   }

	   public String getPic()
	   {
	      return picProperty().get();
	   }

	   public final void setPic(String pic)
	   {
	      this.picProperty().set(pic);
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

	public SimpleBooleanProperty twentyOverHeightySalesOnlyProperty()
	{
		if (twentyOverHeightySalesOnly == null)
		{
			twentyOverHeightySalesOnly = new SimpleBooleanProperty();
		}
		return twentyOverHeightySalesOnly;
	}

	public Boolean getTwentyOverHeightySalesOnly()
	{
		return twentyOverHeightySalesOnlyProperty().get();
	}

	public final void setTwentyOverHeightySalesOnly(Boolean twentyOverHeightySalesOnly)
	{
		if (twentyOverHeightySalesOnly == null)
			twentyOverHeightySalesOnly = Boolean.FALSE;
		this.twentyOverHeightySalesOnlyProperty().set(twentyOverHeightySalesOnly);
	}

	public SimpleBooleanProperty nonTaxableSalesOnlyProperty()
	{
		if (nonTaxableSalesOnly == null)
		{
			nonTaxableSalesOnly = new SimpleBooleanProperty();
		}
		return nonTaxableSalesOnly;
	}

	public Boolean getNonTaxableSalesOnly()
	{
		return nonTaxableSalesOnlyProperty().get();
	}

	public final void setNonTaxableSalesOnly(Boolean nonTaxableSalesOnly)
	{
		if (nonTaxableSalesOnly == null)
			nonTaxableSalesOnly = Boolean.FALSE;
		this.nonTaxableSalesOnlyProperty().set(nonTaxableSalesOnly);
	}

	public SimpleBooleanProperty taxableSalesOnlyProperty()
	{
		if (taxableSalesOnly == null)
		{
			taxableSalesOnly = new SimpleBooleanProperty();
		}
		return taxableSalesOnly;
	}

	public Boolean getTaxableSalesOnly()
	{
		return taxableSalesOnlyProperty().get();
	}

	public final void setTaxableSalesOnly(Boolean taxableSalesOnly)
	{
		if (taxableSalesOnly == null)
			taxableSalesOnly = Boolean.FALSE;
		this.taxableSalesOnlyProperty().set(taxableSalesOnly);
	}

	public String toString()
	{
		return PropertyReader.buildToString(this, "beginDate","endDate");
	}


}
