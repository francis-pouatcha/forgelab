package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javaext.format.DateFormatPattern;
import javax.validation.constraints.Size;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("DeliveryItem_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "internalPic", "secondaryPic", "mainPic", "article.articleName",
      "articleName", "initialQty", "finalQty", "totalPurchasingPrice",
      "totalDiscount", "totalSalesPrice" })
@ToStringField({ "article.articleName", "articleName" })
public class DeliveryItem
{

   private Long id;
   private int version;

   @Description("DeliveryItem_internalPic_description")
   private SimpleStringProperty internalPic;
   @Description("DeliveryItem_secondaryPic_description")
   private SimpleStringProperty secondaryPic;
   @Description("DeliveryItem_mainPic_description")
   private SimpleStringProperty mainPic;
   @Description("DeliveryItem_articleName_description")
   private SimpleStringProperty articleName;
   @Description("DeliveryItem_initialQty_description")
   private SimpleObjectProperty<BigDecimal> initialQty;
   @Description("DeliveryItem_finalQty_description")
   private SimpleObjectProperty<BigDecimal> finalQty;
   @Description("DeliveryItem_totalPurchasingPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalPurchasingPrice;
   @Description("DeliveryItem_totalDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalDiscount;
   @Description("DeliveryItem_totalSalesPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalSalesPrice;
   @Description("DeliveryItem_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> creationDate;
   @Description("DeliveryItem_productRecCreated_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> productRecCreated;
   @Description("DeliveryItem_delivery_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = Delivery.class)
   private SimpleObjectProperty<DeliveryItemDelivery> delivery;
   @Description("DeliveryItem_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<DeliveryItemArticle> article;

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

   public SimpleStringProperty internalPicProperty()
   {
      if (internalPic == null)
      {
         internalPic = new SimpleStringProperty();
      }
      return internalPic;
   }

   @Size(min = 7, message = "DeliveryItem_internalPic_Size_validation")
   public String getInternalPic()
   {
      return internalPicProperty().get();
   }

   public final void setInternalPic(String internalPic)
   {
      this.internalPicProperty().set(internalPic);
   }

   public SimpleStringProperty secondaryPicProperty()
   {
      if (secondaryPic == null)
      {
         secondaryPic = new SimpleStringProperty();
      }
      return secondaryPic;
   }

   @Size(min = 7, message = "DeliveryItem_secondaryPic_Size_validation")
   public String getSecondaryPic()
   {
      return secondaryPicProperty().get();
   }

   public final void setSecondaryPic(String secondaryPic)
   {
      this.secondaryPicProperty().set(secondaryPic);
   }

   public SimpleStringProperty mainPicProperty()
   {
      if (mainPic == null)
      {
         mainPic = new SimpleStringProperty();
      }
      return mainPic;
   }

   @Size(min = 7, message = "DeliveryItem_mainPic_Size_validation")
   public String getMainPic()
   {
      return mainPicProperty().get();
   }

   public final void setMainPic(String mainPic)
   {
      this.mainPicProperty().set(mainPic);
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

   public SimpleObjectProperty<BigDecimal> initialQtyProperty()
   {
      if (initialQty == null)
      {
         initialQty = new SimpleObjectProperty<BigDecimal>();
      }
      return initialQty;
   }

   public BigDecimal getInitialQty()
   {
      return initialQtyProperty().get();
   }

   public final void setInitialQty(BigDecimal initialQty)
   {
      this.initialQtyProperty().set(initialQty);
   }

   public SimpleObjectProperty<BigDecimal> finalQtyProperty()
   {
      if (finalQty == null)
      {
         finalQty = new SimpleObjectProperty<BigDecimal>();
      }
      return finalQty;
   }

   public BigDecimal getFinalQty()
   {
      return finalQtyProperty().get();
   }

   public final void setFinalQty(BigDecimal finalQty)
   {
      this.finalQtyProperty().set(finalQty);
   }

   public SimpleObjectProperty<BigDecimal> totalPurchasingPriceProperty()
   {
      if (totalPurchasingPrice == null)
      {
         totalPurchasingPrice = new SimpleObjectProperty<BigDecimal>();
      }
      return totalPurchasingPrice;
   }

   @NotNull(message = "DeliveryItem_totalPurchasingPrice_NotNull_validation")
   public BigDecimal getTotalPurchasingPrice()
   {
      return totalPurchasingPriceProperty().get();
   }

   public final void setTotalPurchasingPrice(BigDecimal totalPurchasingPrice)
   {
      this.totalPurchasingPriceProperty().set(totalPurchasingPrice);
   }

   public SimpleObjectProperty<BigDecimal> totalDiscountProperty()
   {
      if (totalDiscount == null)
      {
         totalDiscount = new SimpleObjectProperty<BigDecimal>();
      }
      return totalDiscount;
   }

   public BigDecimal getTotalDiscount()
   {
      return totalDiscountProperty().get();
   }

   public final void setTotalDiscount(BigDecimal totalDiscount)
   {
      this.totalDiscountProperty().set(totalDiscount);
   }

   public SimpleObjectProperty<BigDecimal> totalSalesPriceProperty()
   {
      if (totalSalesPrice == null)
      {
         totalSalesPrice = new SimpleObjectProperty<BigDecimal>();
      }
      return totalSalesPrice;
   }

   public BigDecimal getTotalSalesPrice()
   {
      return totalSalesPriceProperty().get();
   }

   public final void setTotalSalesPrice(BigDecimal totalSalesPrice)
   {
      this.totalSalesPriceProperty().set(totalSalesPrice);
   }

   public SimpleObjectProperty<Calendar> creationDateProperty()
   {
      if (creationDate == null)
      {
         creationDate = new SimpleObjectProperty<Calendar>();
      }
      return creationDate;
   }

   public Calendar getCreationDate()
   {
      return creationDateProperty().get();
   }

   public final void setCreationDate(Calendar creationDate)
   {
      this.creationDateProperty().set(creationDate);
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

   public SimpleObjectProperty<DeliveryItemDelivery> deliveryProperty()
   {
      if (delivery == null)
      {
         delivery = new SimpleObjectProperty<DeliveryItemDelivery>(new DeliveryItemDelivery());
      }
      return delivery;
   }

   public DeliveryItemDelivery getDelivery()
   {
      return deliveryProperty().get();
   }

   public final void setDelivery(DeliveryItemDelivery delivery)
   {
      if (delivery == null)
      {
         delivery = new DeliveryItemDelivery();
      }
      PropertyReader.copy(delivery, getDelivery());
   }

   public SimpleObjectProperty<DeliveryItemArticle> articleProperty()
   {
      if (article == null)
      {
         article = new SimpleObjectProperty<DeliveryItemArticle>(new DeliveryItemArticle());
      }
      return article;
   }

   @NotNull(message = "DeliveryItem_article_NotNull_validation")
   public DeliveryItemArticle getArticle()
   {
      return articleProperty().get();
   }

   public final void setArticle(DeliveryItemArticle article)
   {
      if (article == null)
      {
         article = new DeliveryItemArticle();
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
      DeliveryItem other = (DeliveryItem) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "articleName", "articleName");
   }
}