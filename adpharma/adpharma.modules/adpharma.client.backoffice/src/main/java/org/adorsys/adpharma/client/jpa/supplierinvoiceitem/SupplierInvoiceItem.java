package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("SupplierInvoiceItem_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "internalPic", "article.articleName", "deliveryQty" })
@ListField({ "internalPic", "article.articleName", "deliveryQty",
      "purchasePricePU", "salesPricePU", "amountReturn", "totalPurchasePrice" })
public class SupplierInvoiceItem implements Cloneable
{

   private Long id;
   private int version;

   @Description("SupplierInvoiceItem_internalPic_description")
   private SimpleStringProperty internalPic;
   @Description("SupplierInvoiceItem_deliveryQty_description")
   private SimpleObjectProperty<BigDecimal> deliveryQty;
   @Description("SupplierInvoiceItem_purchasePricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> purchasePricePU;
   @Description("SupplierInvoiceItem_salesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> salesPricePU;
   @Description("SupplierInvoiceItem_amountReturn_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountReturn;
   @Description("SupplierInvoiceItem_totalPurchasePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalPurchasePrice;
   @Description("SupplierInvoiceItem_invoice_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = SupplierInvoice.class)
   private SimpleObjectProperty<SupplierInvoiceItemInvoice> invoice;
   @Description("SupplierInvoiceItem_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<SupplierInvoiceItemArticle> article;

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

   public String getInternalPic()
   {
      return internalPicProperty().get();
   }

   public final void setInternalPic(String internalPic)
   {
      this.internalPicProperty().set(internalPic);
   }

   public SimpleObjectProperty<BigDecimal> deliveryQtyProperty()
   {
      if (deliveryQty == null)
      {
         deliveryQty = new SimpleObjectProperty<BigDecimal>();
      }
      return deliveryQty;
   }

   public BigDecimal getDeliveryQty()
   {
      return deliveryQtyProperty().get();
   }

   public final void setDeliveryQty(BigDecimal deliveryQty)
   {
      this.deliveryQtyProperty().set(deliveryQty);
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

   public SimpleObjectProperty<BigDecimal> amountReturnProperty()
   {
      if (amountReturn == null)
      {
         amountReturn = new SimpleObjectProperty<BigDecimal>();
      }
      return amountReturn;
   }

   public BigDecimal getAmountReturn()
   {
      return amountReturnProperty().get();
   }

   public final void setAmountReturn(BigDecimal amountReturn)
   {
      this.amountReturnProperty().set(amountReturn);
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

   public SimpleObjectProperty<SupplierInvoiceItemInvoice> invoiceProperty()
   {
      if (invoice == null)
      {
         invoice = new SimpleObjectProperty<SupplierInvoiceItemInvoice>(new SupplierInvoiceItemInvoice());
      }
      return invoice;
   }

   public SupplierInvoiceItemInvoice getInvoice()
   {
      return invoiceProperty().get();
   }

   public final void setInvoice(SupplierInvoiceItemInvoice invoice)
   {
      if (invoice == null)
      {
         invoice = new SupplierInvoiceItemInvoice();
      }
      PropertyReader.copy(invoice, getInvoice());
      invoiceProperty().setValue(ObjectUtils.clone(getInvoice()));
   }

   public SimpleObjectProperty<SupplierInvoiceItemArticle> articleProperty()
   {
      if (article == null)
      {
         article = new SimpleObjectProperty<SupplierInvoiceItemArticle>(new SupplierInvoiceItemArticle());
      }
      return article;
   }

   public SupplierInvoiceItemArticle getArticle()
   {
      return articleProperty().get();
   }

   public final void setArticle(SupplierInvoiceItemArticle article)
   {
      if (article == null)
      {
         article = new SupplierInvoiceItemArticle();
      }
      PropertyReader.copy(article, getArticle());
      articleProperty().setValue(ObjectUtils.clone(getArticle()));
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
      SupplierInvoiceItem other = (SupplierInvoiceItem) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "internalPic", "articleName", "deliveryQty");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
      SupplierInvoiceItemInvoice f = invoice.get();
      f.setId(null);
      f.setVersion(0);
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      SupplierInvoiceItem e = new SupplierInvoiceItem();
      e.id = id;
      e.version = version;

      e.internalPic = internalPic;
      e.deliveryQty = deliveryQty;
      e.purchasePricePU = purchasePricePU;
      e.salesPricePU = salesPricePU;
      e.amountReturn = amountReturn;
      e.totalPurchasePrice = totalPurchasePrice;
      e.invoice = invoice;
      e.article = article;
      return e;
   }
}