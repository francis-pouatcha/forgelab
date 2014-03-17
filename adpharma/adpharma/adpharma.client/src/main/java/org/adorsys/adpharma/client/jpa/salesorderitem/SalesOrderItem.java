package org.adorsys.adpharma.client.jpa.salesorderitem;

import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import javax.validation.constraints.Size;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("SalesOrderItem_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "orderedQty", "returnedQty", "deliveredQty", "salesPricePU",
      "totalSalePrice", "internalPic", "article.articleName" })
@ToStringField("article.articleName")
public class SalesOrderItem
{

   private Long id;
   private int version;

   @Description("SalesOrderItem_internalPic_description")
   private SimpleStringProperty internalPic;
   @Description("SalesOrderItem_orderedQty_description")
   private SimpleObjectProperty<BigDecimal> orderedQty;
   @Description("SalesOrderItem_returnedQty_description")
   private SimpleObjectProperty<BigDecimal> returnedQty;
   @Description("SalesOrderItem_deliveredQty_description")
   private SimpleObjectProperty<BigDecimal> deliveredQty;
   @Description("SalesOrderItem_salesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> salesPricePU;
   @Description("SalesOrderItem_totalSalePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalSalePrice;
   @Description("SalesOrderItem_recordDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordDate;
   @Description("SalesOrderItem_salesOrder_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = SalesOrder.class)
   private SimpleObjectProperty<SalesOrderItemSalesOrder> salesOrder;
   @Description("SalesOrderItem_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<SalesOrderItemArticle> article;

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

   @Size(min = 7, message = "SalesOrderItem_internalPic_Size_validation")
   public String getInternalPic()
   {
      return internalPicProperty().get();
   }

   public final void setInternalPic(String internalPic)
   {
      this.internalPicProperty().set(internalPic);
   }

   public SimpleObjectProperty<BigDecimal> orderedQtyProperty()
   {
      if (orderedQty == null)
      {
         orderedQty = new SimpleObjectProperty<BigDecimal>();
      }
      return orderedQty;
   }

   public BigDecimal getOrderedQty()
   {
      return orderedQtyProperty().get();
   }

   public final void setOrderedQty(BigDecimal orderedQty)
   {
      this.orderedQtyProperty().set(orderedQty);
   }

   public SimpleObjectProperty<BigDecimal> returnedQtyProperty()
   {
      if (returnedQty == null)
      {
         returnedQty = new SimpleObjectProperty<BigDecimal>();
      }
      return returnedQty;
   }

   public BigDecimal getReturnedQty()
   {
      return returnedQtyProperty().get();
   }

   public final void setReturnedQty(BigDecimal returnedQty)
   {
      this.returnedQtyProperty().set(returnedQty);
   }

   public SimpleObjectProperty<BigDecimal> deliveredQtyProperty()
   {
      if (deliveredQty == null)
      {
         deliveredQty = new SimpleObjectProperty<BigDecimal>();
      }
      return deliveredQty;
   }

   public BigDecimal getDeliveredQty()
   {
      return deliveredQtyProperty().get();
   }

   public final void setDeliveredQty(BigDecimal deliveredQty)
   {
      this.deliveredQtyProperty().set(deliveredQty);
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

   public SimpleObjectProperty<BigDecimal> totalSalePriceProperty()
   {
      if (totalSalePrice == null)
      {
         totalSalePrice = new SimpleObjectProperty<BigDecimal>();
      }
      return totalSalePrice;
   }

   public BigDecimal getTotalSalePrice()
   {
      return totalSalePriceProperty().get();
   }

   public final void setTotalSalePrice(BigDecimal totalSalePrice)
   {
      this.totalSalePriceProperty().set(totalSalePrice);
   }

   public SimpleObjectProperty<Calendar> recordDateProperty()
   {
      if (recordDate == null)
      {
         recordDate = new SimpleObjectProperty<Calendar>();
      }
      return recordDate;
   }

   public Calendar getRecordDate()
   {
      return recordDateProperty().get();
   }

   public final void setRecordDate(Calendar recordDate)
   {
      this.recordDateProperty().set(recordDate);
   }

   public SimpleObjectProperty<SalesOrderItemSalesOrder> salesOrderProperty()
   {
      if (salesOrder == null)
      {
         salesOrder = new SimpleObjectProperty<SalesOrderItemSalesOrder>(new SalesOrderItemSalesOrder());
      }
      return salesOrder;
   }

   public SalesOrderItemSalesOrder getSalesOrder()
   {
      return salesOrderProperty().get();
   }

   public final void setSalesOrder(SalesOrderItemSalesOrder salesOrder)
   {
      if (salesOrder == null)
      {
         salesOrder = new SalesOrderItemSalesOrder();
      }
      PropertyReader.copy(salesOrder, getSalesOrder());
   }

   public SimpleObjectProperty<SalesOrderItemArticle> articleProperty()
   {
      if (article == null)
      {
         article = new SimpleObjectProperty<SalesOrderItemArticle>(new SalesOrderItemArticle());
      }
      return article;
   }

   @NotNull(message = "SalesOrderItem_article_NotNull_validation")
   public SalesOrderItemArticle getArticle()
   {
      return articleProperty().get();
   }

   public final void setArticle(SalesOrderItemArticle article)
   {
      if (article == null)
      {
         article = new SalesOrderItemArticle();
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
      SalesOrderItem other = (SalesOrderItem) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "articleName");
   }
}