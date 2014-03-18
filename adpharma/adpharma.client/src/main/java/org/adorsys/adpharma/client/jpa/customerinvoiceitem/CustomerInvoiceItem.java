package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("CustomerInvoiceItem_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "internalPic", "article.articleName", "purchasedQty" })
@ListField({ "internalPic", "article.articleName", "purchasedQty",
      "salesPricePU", "totalSalesPrice" })
public class CustomerInvoiceItem
{

   private Long id;
   private int version;

   @Description("CustomerInvoiceItem_internalPic_description")
   private SimpleStringProperty internalPic;
   @Description("CustomerInvoiceItem_purchasedQty_description")
   private SimpleObjectProperty<BigDecimal> purchasedQty;
   @Description("CustomerInvoiceItem_salesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> salesPricePU;
   @Description("CustomerInvoiceItem_totalSalesPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalSalesPrice;
   @Description("CustomerInvoiceItem_invoice_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = CustomerInvoice.class)
   private SimpleObjectProperty<CustomerInvoiceItemInvoice> invoice;
   @Description("CustomerInvoiceItem_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<CustomerInvoiceItemArticle> article;

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

   public SimpleObjectProperty<BigDecimal> purchasedQtyProperty()
   {
      if (purchasedQty == null)
      {
         purchasedQty = new SimpleObjectProperty<BigDecimal>();
      }
      return purchasedQty;
   }

   public BigDecimal getPurchasedQty()
   {
      return purchasedQtyProperty().get();
   }

   public final void setPurchasedQty(BigDecimal purchasedQty)
   {
      this.purchasedQtyProperty().set(purchasedQty);
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

   public SimpleObjectProperty<CustomerInvoiceItemInvoice> invoiceProperty()
   {
      if (invoice == null)
      {
         invoice = new SimpleObjectProperty<CustomerInvoiceItemInvoice>(new CustomerInvoiceItemInvoice());
      }
      return invoice;
   }

   public CustomerInvoiceItemInvoice getInvoice()
   {
      return invoiceProperty().get();
   }

   public final void setInvoice(CustomerInvoiceItemInvoice invoice)
   {
      if (invoice == null)
      {
         invoice = new CustomerInvoiceItemInvoice();
      }
      PropertyReader.copy(invoice, getInvoice());
   }

   public SimpleObjectProperty<CustomerInvoiceItemArticle> articleProperty()
   {
      if (article == null)
      {
         article = new SimpleObjectProperty<CustomerInvoiceItemArticle>(new CustomerInvoiceItemArticle());
      }
      return article;
   }

   public CustomerInvoiceItemArticle getArticle()
   {
      return articleProperty().get();
   }

   public final void setArticle(CustomerInvoiceItemArticle article)
   {
      if (article == null)
      {
         article = new CustomerInvoiceItemArticle();
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
      CustomerInvoiceItem other = (CustomerInvoiceItem) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "internalPic", "articleName", "purchasedQty");
   }
}