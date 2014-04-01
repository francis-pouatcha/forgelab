package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ProcurementOrderItem_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "mainPic", "secondaryPic", "articleName", "article.articleName",
      "expirationDate", "qtyOrdered", "freeQuantity", "stockQuantity",
      "salesPricePU", "purchasePricePU", "totalPurchasePrice", "valid" })
@ToStringField({ "articleName", "article.articleName", "qtyOrdered" })
public class ProcurementOrderItem implements Cloneable
{

   private Long id;
   private int version;

   @Description("ProcurementOrderItem_mainPic_description")
   private SimpleStringProperty mainPic;
   @Description("ProcurementOrderItem_secondaryPic_description")
   private SimpleStringProperty secondaryPic;
   @Description("ProcurementOrderItem_articleName_description")
   private SimpleStringProperty articleName;
   @Description("ProcurementOrderItem_valid_description")
   private SimpleBooleanProperty valid;
   @Description("ProcurementOrderItem_qtyOrdered_description")
   private SimpleObjectProperty<BigDecimal> qtyOrdered;
   @Description("ProcurementOrderItem_availableQty_description")
   private SimpleObjectProperty<BigDecimal> availableQty;
   @Description("ProcurementOrderItem_freeQuantity_description")
   private SimpleObjectProperty<BigDecimal> freeQuantity;
   @Description("ProcurementOrderItem_stockQuantity_description")
   private SimpleObjectProperty<BigDecimal> stockQuantity;
   @Description("ProcurementOrderItem_salesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> salesPricePU;
   @Description("ProcurementOrderItem_purchasePricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> purchasePricePU;
   @Description("ProcurementOrderItem_totalPurchasePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalPurchasePrice;
   @Description("ProcurementOrderItem_expirationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> expirationDate;
   @Description("ProcurementOrderItem_productRecCreated_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm ")
   private SimpleObjectProperty<Calendar> productRecCreated;
   @Description("ProcurementOrderItem_procurementOrder_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = ProcurementOrder.class)
   private SimpleObjectProperty<ProcurementOrderItemProcurementOrder> procurementOrder;
   @Description("ProcurementOrderItem_article_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<ProcurementOrderItemArticle> article;
   @Description("ProcurementOrderItem_creatingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<ProcurementOrderItemCreatingUser> creatingUser;

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

   public SimpleStringProperty mainPicProperty()
   {
      if (mainPic == null)
      {
         mainPic = new SimpleStringProperty();
      }
      return mainPic;
   }

   @Size(min = 7, message = "ProcurementOrderItem_mainPic_Size_validation")
   public String getMainPic()
   {
      return mainPicProperty().get();
   }

   public final void setMainPic(String mainPic)
   {
      this.mainPicProperty().set(mainPic);
   }

   public SimpleStringProperty secondaryPicProperty()
   {
      if (secondaryPic == null)
      {
         secondaryPic = new SimpleStringProperty();
      }
      return secondaryPic;
   }

   @Size(min = 7, message = "ProcurementOrderItem_secondaryPic_Size_validation")
   public String getSecondaryPic()
   {
      return secondaryPicProperty().get();
   }

   public final void setSecondaryPic(String secondaryPic)
   {
      this.secondaryPicProperty().set(secondaryPic);
   }

   public SimpleStringProperty articleNameProperty()
   {
      if (articleName == null)
      {
         articleName = new SimpleStringProperty();
      }
      return articleName;
   }

   @NotNull(message = "ProcurementOrderItem_articleName_NotNull_validation")
   public String getArticleName()
   {
      return articleNameProperty().get();
   }

   public final void setArticleName(String articleName)
   {
      this.articleNameProperty().set(articleName);
   }

   public SimpleBooleanProperty validProperty()
   {
      if (valid == null)
      {
         valid = new SimpleBooleanProperty();
      }
      return valid;
   }

   public Boolean getValid()
   {
      return validProperty().get();
   }

   public final void setValid(Boolean valid)
   {
      if (valid == null)
         valid = Boolean.FALSE;
      this.validProperty().set(valid);
   }

   public SimpleObjectProperty<BigDecimal> qtyOrderedProperty()
   {
      if (qtyOrdered == null)
      {
         qtyOrdered = new SimpleObjectProperty<BigDecimal>();
      }
      return qtyOrdered;
   }

   public BigDecimal getQtyOrdered()
   {
      return qtyOrderedProperty().get();
   }

   public final void setQtyOrdered(BigDecimal qtyOrdered)
   {
      this.qtyOrderedProperty().set(qtyOrdered);
   }

   public SimpleObjectProperty<BigDecimal> availableQtyProperty()
   {
      if (availableQty == null)
      {
         availableQty = new SimpleObjectProperty<BigDecimal>();
      }
      return availableQty;
   }

   public BigDecimal getAvailableQty()
   {
      return availableQtyProperty().get();
   }

   public final void setAvailableQty(BigDecimal availableQty)
   {
      this.availableQtyProperty().set(availableQty);
   }

   public SimpleObjectProperty<BigDecimal> freeQuantityProperty()
   {
      if (freeQuantity == null)
      {
         freeQuantity = new SimpleObjectProperty<BigDecimal>();
      }
      return freeQuantity;
   }

   public BigDecimal getFreeQuantity()
   {
      return freeQuantityProperty().get();
   }

   public final void setFreeQuantity(BigDecimal freeQuantity)
   {
      this.freeQuantityProperty().set(freeQuantity);
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

   public SimpleObjectProperty<BigDecimal> salesPricePUProperty()
   {
      if (salesPricePU == null)
      {
         salesPricePU = new SimpleObjectProperty<BigDecimal>();
      }
      return salesPricePU;
   }

   public BigDecimal getSalesPricePU()
   {
      return salesPricePUProperty().get();
   }

   public final void setSalesPricePU(BigDecimal salesPricePU)
   {
      this.salesPricePUProperty().set(salesPricePU);
   }

   public SimpleObjectProperty<BigDecimal> purchasePricePUProperty()
   {
      if (purchasePricePU == null)
      {
         purchasePricePU = new SimpleObjectProperty<BigDecimal>();
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

   public SimpleObjectProperty<BigDecimal> totalPurchasePriceProperty()
   {
      if (totalPurchasePrice == null)
      {
         totalPurchasePrice = new SimpleObjectProperty<BigDecimal>();
      }
      return totalPurchasePrice;
   }

   public BigDecimal getTotalPurchasePrice()
   {
      return totalPurchasePriceProperty().get();
   }

   public final void setTotalPurchasePrice(BigDecimal totalPurchasePrice)
   {
      this.totalPurchasePriceProperty().set(totalPurchasePrice);
   }

   public SimpleObjectProperty<Calendar> expirationDateProperty()
   {
      if (expirationDate == null)
      {
         expirationDate = new SimpleObjectProperty<Calendar>();
      }
      return expirationDate;
   }

   public Calendar getExpirationDate()
   {
      return expirationDateProperty().get();
   }

   public final void setExpirationDate(Calendar expirationDate)
   {
      this.expirationDateProperty().set(expirationDate);
   }

   public SimpleObjectProperty<Calendar> productRecCreatedProperty()
   {
      if (productRecCreated == null)
      {
         productRecCreated = new SimpleObjectProperty<Calendar>();
      }
      return productRecCreated;
   }

   public Calendar getProductRecCreated()
   {
      return productRecCreatedProperty().get();
   }

   public final void setProductRecCreated(Calendar productRecCreated)
   {
      this.productRecCreatedProperty().set(productRecCreated);
   }

   public SimpleObjectProperty<ProcurementOrderItemProcurementOrder> procurementOrderProperty()
   {
      if (procurementOrder == null)
      {
         procurementOrder = new SimpleObjectProperty<ProcurementOrderItemProcurementOrder>(new ProcurementOrderItemProcurementOrder());
      }
      return procurementOrder;
   }

   public ProcurementOrderItemProcurementOrder getProcurementOrder()
   {
      return procurementOrderProperty().get();
   }

   public final void setProcurementOrder(ProcurementOrderItemProcurementOrder procurementOrder)
   {
      if (procurementOrder == null)
      {
         procurementOrder = new ProcurementOrderItemProcurementOrder();
      }
      PropertyReader.copy(procurementOrder, getProcurementOrder());
      procurementOrderProperty().setValue(ObjectUtils.clone(getProcurementOrder()));
   }

   public SimpleObjectProperty<ProcurementOrderItemArticle> articleProperty()
   {
      if (article == null)
      {
         article = new SimpleObjectProperty<ProcurementOrderItemArticle>(new ProcurementOrderItemArticle());
      }
      return article;
   }

   @NotNull(message = "ProcurementOrderItem_article_NotNull_validation")
   public ProcurementOrderItemArticle getArticle()
   {
      return articleProperty().get();
   }

   public final void setArticle(ProcurementOrderItemArticle article)
   {
      if (article == null)
      {
         article = new ProcurementOrderItemArticle();
      }
      PropertyReader.copy(article, getArticle());
      articleProperty().setValue(ObjectUtils.clone(getArticle()));
   }

   public SimpleObjectProperty<ProcurementOrderItemCreatingUser> creatingUserProperty()
   {
      if (creatingUser == null)
      {
         creatingUser = new SimpleObjectProperty<ProcurementOrderItemCreatingUser>(new ProcurementOrderItemCreatingUser());
      }
      return creatingUser;
   }

   @NotNull(message = "ProcurementOrderItem_creatingUser_NotNull_validation")
   public ProcurementOrderItemCreatingUser getCreatingUser()
   {
      return creatingUserProperty().get();
   }

   public final void setCreatingUser(ProcurementOrderItemCreatingUser creatingUser)
   {
      if (creatingUser == null)
      {
         creatingUser = new ProcurementOrderItemCreatingUser();
      }
      PropertyReader.copy(creatingUser, getCreatingUser());
      creatingUserProperty().setValue(ObjectUtils.clone(getCreatingUser()));
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
      ProcurementOrderItem other = (ProcurementOrderItem) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "articleName", "articleName", "qtyOrdered");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
      ProcurementOrderItemProcurementOrder f = procurementOrder.get();
      f.setId(null);
      f.setVersion(0);
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      ProcurementOrderItem e = new ProcurementOrderItem();
      e.id = id;
      e.version = version;

      e.mainPic = mainPic;
      e.secondaryPic = secondaryPic;
      e.articleName = articleName;
      e.valid = valid;
      e.qtyOrdered = qtyOrdered;
      e.availableQty = availableQty;
      e.freeQuantity = freeQuantity;
      e.stockQuantity = stockQuantity;
      e.salesPricePU = salesPricePU;
      e.purchasePricePU = purchasePricePU;
      e.totalPurchasePrice = totalPurchasePrice;
      e.expirationDate = expirationDate;
      e.productRecCreated = productRecCreated;
      e.procurementOrder = procurementOrder;
      e.article = article;
      e.creatingUser = creatingUser;
      return e;
   }
}