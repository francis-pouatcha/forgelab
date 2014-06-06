package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("WareHouseArticleLot_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "internalCip", "mainCip", "secondaryCip", "articleName", "stockQuantity","wareHouse","articleLot" })
@ToStringField({ "articleName", "articleLot.articleName" })
public class WareHouseArticleLot implements Cloneable
{
	 private Long id;
	   private int version;

	   @Description("WareHouseArticleLot_internalCip_description")
	   private SimpleStringProperty internalCip;
	   @Description("WareHouseArticleLot_mainCip_description")
	   private SimpleStringProperty mainCip;
	   @Description("WareHouseArticleLot_secondaryCip_description")
	   private SimpleStringProperty secondaryCip;
	   @Description("WareHouseArticleLot_articleName_description")
	   private SimpleStringProperty articleName;
	   
	   @Description("WareHouseArticleLot_stockQuantity_description")
	   private SimpleObjectProperty<BigDecimal> stockQuantity;
	   
	   @Description("WareHouseArticleLot_wareHouse_description")
	   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = WareHouse.class)
	   private SimpleObjectProperty<WareHouseArticleLotWareHouse> wareHouse;
	   @Description("WareHouseArticleLot_articleLot_description")
	   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = ArticleLot.class)
	   private SimpleObjectProperty<WareHouseArticleLotArticleLot> articleLot;

	   public Long getId()
	   {
	      return id;
	   }

	   public final void setId(Long id)
	   {
	      this.id = id;
	   }

	   public int getVersion()
	   {
	      return version;
	   }

	   public final void setVersion(int version)
	   {
	      this.version = version;
	   }
	   
	   public SimpleObjectProperty<WareHouseArticleLotArticleLot> articleLotProperty()
	   {
	      if (articleLot == null)
	      {
	    	  articleLot = new SimpleObjectProperty<WareHouseArticleLotArticleLot>(new WareHouseArticleLotArticleLot());
	      }
	      return articleLot;
	   }

	   public WareHouseArticleLotArticleLot getArticleLot()
	   {
	      return articleLotProperty().get();
	   }

	   public final void setArticleLot(WareHouseArticleLotArticleLot wareHouse)
	   {
	      if (wareHouse == null)
	      {
	    	  wareHouse = new WareHouseArticleLotArticleLot();
	      }
	      PropertyReader.copy(wareHouse, getArticleLot());
	      articleLotProperty().setValue(ObjectUtils.clone(getArticleLot()));
	   }

	   public SimpleStringProperty internalCipProperty()
	   {
	      if (internalCip == null)
	      {
	    	  internalCip = new SimpleStringProperty();
	      }
	      return internalCip;
	   }

	   public String getInternalCip()
	   {
	      return internalCipProperty().get();
	   }

	   public final void setInternalCip(String internalPic)
	   {
	      this.internalCipProperty().set(internalPic);
	   }

	   public SimpleStringProperty mainCipProperty()
	   {
	      if (mainCip == null)
	      {
	    	  mainCip = new SimpleStringProperty();
	      }
	      return mainCip;
	   }

	   public String getMainCip()
	   {
	      return mainCipProperty().get();
	   }

	   public final void setMainCip(String mainPic)
	   {
	      this.mainCipProperty().set(mainPic);
	   }

	   public SimpleStringProperty secondaryCipProperty()
	   {
	      if (secondaryCip == null)
	      {
	    	  secondaryCip = new SimpleStringProperty();
	      }
	      return secondaryCip;
	   }

	   public String getSecondaryCip()
	   {
	      return secondaryCipProperty().get();
	   }

	   public final void setSecondaryCip(String secondaryPic)
	   {
	      this.secondaryCipProperty().set(secondaryPic);
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

	   public SimpleObjectProperty<BigDecimal> stockQuantityProperty()
	   {
	      if (stockQuantity == null)
	      {
	         stockQuantity = new SimpleObjectProperty<BigDecimal>();
	      }
	      return stockQuantity;
	   }

	   public BigDecimal getStockQuantity()
	   {
	      return stockQuantityProperty().get();
	   }

	   public final void setStockQuantity(BigDecimal stockQuantity)
	   {
	      this.stockQuantityProperty().set(stockQuantity);
	   }

	  

	   public SimpleObjectProperty<WareHouseArticleLotWareHouse> wareHouseProperty()
	   {
	      if (wareHouse == null)
	      {
	    	  wareHouse = new SimpleObjectProperty<WareHouseArticleLotWareHouse>(new WareHouseArticleLotWareHouse());
	      }
	      return wareHouse;
	   }

	   public WareHouseArticleLotWareHouse getWareHouse()
	   {
	      return wareHouseProperty().get();
	   }

	   public final void setWareHouse(WareHouseArticleLotWareHouse wareHouse)
	   {
	      if (wareHouse == null)
	      {
	    	  wareHouse = new WareHouseArticleLotWareHouse();
	      }
	      PropertyReader.copy(wareHouse, getWareHouse());
	      wareHouseProperty().setValue(ObjectUtils.clone(getWareHouse()));
	   }

	   public String toString()
	   {
	      return PropertyReader.buildToString(this, "articleName");
	   }

	   public void cleanIds()
	   {
	      id = null;
	      version = 0;
	   }

	   @Override
	   public Object clone() throws CloneNotSupportedException
	   {
		   WareHouseArticleLot e = new WareHouseArticleLot();
	      e.id = id;
	      e.version = version;

	      e.internalCip = internalCip;
	      e.mainCip = mainCip;
	      e.secondaryCip = secondaryCip;
	      e.articleName = articleName;
	      e.stockQuantity = stockQuantity;
	      e.wareHouse = wareHouse;
	      e.articleLot = articleLot;
	      return e;
	   }

		public int compareTo(WareHouseArticleLot o) {
			if(o==null) return -1;
			return getInternalCip().compareTo(o.getInternalCip());
		}
}