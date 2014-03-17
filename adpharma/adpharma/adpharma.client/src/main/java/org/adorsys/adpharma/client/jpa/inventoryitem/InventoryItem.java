package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.inventory.Inventory;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("InventoryItem_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "expectedQty", "asseccedQty", "gap", "internalPic",
      "article.articleName" })
@ListField({ "expectedQty", "asseccedQty", "gap", "gapSalesPricePU",
      "gapPurchasePricePU", "gapTotalSalePrice", "gapTotalPurchasePrice",
      "internalPic", "article.articleName" })
public class InventoryItem
{

   private Long id;
   private int version;

   @Description("InventoryItem_gap_description")
   private SimpleLongProperty gap;
   @Description("InventoryItem_internalPic_description")
   private SimpleStringProperty internalPic;
   @Description("InventoryItem_expectedQty_description")
   private SimpleObjectProperty<BigDecimal> expectedQty;
   @Description("InventoryItem_asseccedQty_description")
   private SimpleObjectProperty<BigDecimal> asseccedQty;
   @Description("InventoryItem_gapSalesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> gapSalesPricePU;
   @Description("InventoryItem_gapPurchasePricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> gapPurchasePricePU;
   @Description("InventoryItem_gapTotalSalePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> gapTotalSalePrice;
   @Description("InventoryItem_gapTotalPurchasePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> gapTotalPurchasePrice;
   @Description("InventoryItem_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Description("InventoryItem_inventory_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = Inventory.class)
   private SimpleObjectProperty<InventoryItemInventory> inventory;
   @Description("InventoryItem_recordingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<InventoryItemRecordingUser> recordingUser;
   @Description("InventoryItem_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<InventoryItemArticle> article;

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

   public SimpleLongProperty gapProperty()
   {
      if (gap == null)
      {
         gap = new SimpleLongProperty();
      }
      return gap;
   }

   public Long getGap()
   {
      return gapProperty().get();
   }

   public final void setGap(Long gap)
   {
      this.gapProperty().set(gap);
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

   public SimpleObjectProperty<BigDecimal> expectedQtyProperty()
   {
      if (expectedQty == null)
      {
         expectedQty = new SimpleObjectProperty<BigDecimal>();
      }
      return expectedQty;
   }

   public BigDecimal getExpectedQty()
   {
      return expectedQtyProperty().get();
   }

   public final void setExpectedQty(BigDecimal expectedQty)
   {
      this.expectedQtyProperty().set(expectedQty);
   }

   public SimpleObjectProperty<BigDecimal> asseccedQtyProperty()
   {
      if (asseccedQty == null)
      {
         asseccedQty = new SimpleObjectProperty<BigDecimal>();
      }
      return asseccedQty;
   }

   public BigDecimal getAsseccedQty()
   {
      return asseccedQtyProperty().get();
   }

   public final void setAsseccedQty(BigDecimal asseccedQty)
   {
      this.asseccedQtyProperty().set(asseccedQty);
   }

   public SimpleObjectProperty<BigDecimal> gapSalesPricePUProperty()
   {
      if (gapSalesPricePU == null)
      {
         gapSalesPricePU = new SimpleObjectProperty<BigDecimal>();
      }
      return gapSalesPricePU;
   }

   public BigDecimal getGapSalesPricePU()
   {
      return gapSalesPricePUProperty().get();
   }

   public final void setGapSalesPricePU(BigDecimal gapSalesPricePU)
   {
      this.gapSalesPricePUProperty().set(gapSalesPricePU);
   }

   public SimpleObjectProperty<BigDecimal> gapPurchasePricePUProperty()
   {
      if (gapPurchasePricePU == null)
      {
         gapPurchasePricePU = new SimpleObjectProperty<BigDecimal>();
      }
      return gapPurchasePricePU;
   }

   public BigDecimal getGapPurchasePricePU()
   {
      return gapPurchasePricePUProperty().get();
   }

   public final void setGapPurchasePricePU(BigDecimal gapPurchasePricePU)
   {
      this.gapPurchasePricePUProperty().set(gapPurchasePricePU);
   }

   public SimpleObjectProperty<BigDecimal> gapTotalSalePriceProperty()
   {
      if (gapTotalSalePrice == null)
      {
         gapTotalSalePrice = new SimpleObjectProperty<BigDecimal>();
      }
      return gapTotalSalePrice;
   }

   public BigDecimal getGapTotalSalePrice()
   {
      return gapTotalSalePriceProperty().get();
   }

   public final void setGapTotalSalePrice(BigDecimal gapTotalSalePrice)
   {
      this.gapTotalSalePriceProperty().set(gapTotalSalePrice);
   }

   public SimpleObjectProperty<BigDecimal> gapTotalPurchasePriceProperty()
   {
      if (gapTotalPurchasePrice == null)
      {
         gapTotalPurchasePrice = new SimpleObjectProperty<BigDecimal>();
      }
      return gapTotalPurchasePrice;
   }

   public BigDecimal getGapTotalPurchasePrice()
   {
      return gapTotalPurchasePriceProperty().get();
   }

   public final void setGapTotalPurchasePrice(BigDecimal gapTotalPurchasePrice)
   {
      this.gapTotalPurchasePriceProperty().set(gapTotalPurchasePrice);
   }

   public SimpleObjectProperty<Calendar> recordingDateProperty()
   {
      if (recordingDate == null)
      {
         recordingDate = new SimpleObjectProperty<Calendar>();
      }
      return recordingDate;
   }

   public Calendar getRecordingDate()
   {
      return recordingDateProperty().get();
   }

   public final void setRecordingDate(Calendar recordingDate)
   {
      this.recordingDateProperty().set(recordingDate);
   }

   public SimpleObjectProperty<InventoryItemInventory> inventoryProperty()
   {
      if (inventory == null)
      {
         inventory = new SimpleObjectProperty<InventoryItemInventory>(new InventoryItemInventory());
      }
      return inventory;
   }

   public InventoryItemInventory getInventory()
   {
      return inventoryProperty().get();
   }

   public final void setInventory(InventoryItemInventory inventory)
   {
      if (inventory == null)
      {
         inventory = new InventoryItemInventory();
      }
      PropertyReader.copy(inventory, getInventory());
   }

   public SimpleObjectProperty<InventoryItemRecordingUser> recordingUserProperty()
   {
      if (recordingUser == null)
      {
         recordingUser = new SimpleObjectProperty<InventoryItemRecordingUser>(new InventoryItemRecordingUser());
      }
      return recordingUser;
   }

   @NotNull(message = "InventoryItem_recordingUser_NotNull_validation")
   public InventoryItemRecordingUser getRecordingUser()
   {
      return recordingUserProperty().get();
   }

   public final void setRecordingUser(InventoryItemRecordingUser recordingUser)
   {
      if (recordingUser == null)
      {
         recordingUser = new InventoryItemRecordingUser();
      }
      PropertyReader.copy(recordingUser, getRecordingUser());
   }

   public SimpleObjectProperty<InventoryItemArticle> articleProperty()
   {
      if (article == null)
      {
         article = new SimpleObjectProperty<InventoryItemArticle>(new InventoryItemArticle());
      }
      return article;
   }

   @NotNull(message = "InventoryItem_article_NotNull_validation")
   public InventoryItemArticle getArticle()
   {
      return articleProperty().get();
   }

   public final void setArticle(InventoryItemArticle article)
   {
      if (article == null)
      {
         article = new InventoryItemArticle();
      }
      PropertyReader.copy(article, getArticle());
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      InventoryItem other = (InventoryItem) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "expectedQty", "asseccedQty", "gap", "internalPic", "articleName");
   }
}