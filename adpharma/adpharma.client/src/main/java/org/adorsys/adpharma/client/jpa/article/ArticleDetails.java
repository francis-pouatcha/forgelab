
package org.adorsys.adpharma.client.jpa.article;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ArticleDetails_description")
public class ArticleDetails {
	
	private SimpleStringProperty internalPic;
	
	private SimpleStringProperty mainPic;
	
	private SimpleStringProperty articleName;
	
	private SimpleStringProperty supplier;
	
	private SimpleObjectProperty<Calendar> deliveryDate;
	
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> purchasePricePU;
	
	public SimpleStringProperty mainPicProperty()
	{
		if (mainPic == null)
		{
			mainPic = new SimpleStringProperty();
		}
		return mainPic;
	}

	public String getMainPic()
	{
		return mainPicProperty().get();
	}

	public final void setMainPic(String mainPic)
	{
		this.mainPicProperty().set(mainPic);
	}
	
	
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
	
	public SimpleStringProperty supplierProperty()
	{
		if (supplier == null)
		{
			supplier = new SimpleStringProperty();
		}
		return supplier;
	}

	public String getSupplier()
	{
		return supplierProperty().get();
	}

	public final void setSupplier(String supplier)
	{
		this.supplierProperty().set(supplier);
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
	
	
	public SimpleObjectProperty<BigDecimal> purchasePricePUProperty()
	{
		if (purchasePricePU == null)
		{
			purchasePricePU = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
		}
		return purchasePricePU;
	}

	public BigDecimal getPurchasePricePU()
	{
		return purchasePricePUProperty().get();
	}

	public final void setPurchasePricePU(BigDecimal purchasePricePU)
	{
		this.purchasePricePUProperty().set(purchasePricePU);
	}
	
	public SimpleObjectProperty<Calendar> deliveryDateProperty()
	   {
	      if (deliveryDate == null)
	      {
	    	  deliveryDate = new SimpleObjectProperty<Calendar>();
	      }
	      return deliveryDate;
	   }

	   public Calendar getDeliveryDate()
	   {
	      return deliveryDateProperty().get();
	   }

	   public final void setDeliveryDate(Calendar deliveryDate)
	   {
	      this.deliveryDateProperty().set(deliveryDate);
	   }
	

}
