package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.adpharma.server.jpa.Article;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.SupplierInvoice;

@Entity
@Description("SupplierInvoiceItem_description")
@ToStringField({ "internalPic", "article.articleName", "indexLine", "deliveryQty" })
@ListField({ "internalPic", "article.articleName", "indexLine", "deliveryQty",
      "purchasePricePU", "salesPricePU", "amountReturn", "totalSalesPrice" })
public class SupplierInvoiceItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("SupplierInvoiceItem_internalPic_description")
   private String internalPic;

   @ManyToOne
   @Description("SupplierInvoiceItem_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private Article article;

   @Column
   @Description("SupplierInvoiceItem_indexLine_description")
   private Long indexLine;

   @Column
   @Description("SupplierInvoiceItem_deliveryQty_description")
   private BigDecimal deliveryQty;

   @Column
   @Description("SupplierInvoiceItem_purchasePricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal purchasePricePU;

   @Column
   @Description("SupplierInvoiceItem_salesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal salesPricePU;

   @Column
   @Description("SupplierInvoiceItem_amountReturn_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountReturn;

   @Column
   @Description("SupplierInvoiceItem_totalSalesPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalSalesPrice;

   @ManyToOne
   @Description("SupplierInvoiceItem_invoice_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = SupplierInvoice.class)
   private SupplierInvoice invoice;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((SupplierInvoiceItem) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getInternalPic()
   {
      return this.internalPic;
   }

   public void setInternalPic(final String internalPic)
   {
      this.internalPic = internalPic;
   }

   public Article getArticle()
   {
      return this.article;
   }

   public void setArticle(final Article article)
   {
      this.article = article;
   }

   public Long getIndexLine()
   {
      return this.indexLine;
   }

   public void setIndexLine(final Long indexLine)
   {
      this.indexLine = indexLine;
   }

   public BigDecimal getDeliveryQty()
   {
      return this.deliveryQty;
   }

   public void setDeliveryQty(final BigDecimal deliveryQty)
   {
      this.deliveryQty = deliveryQty;
   }

   public BigDecimal getPurchasePricePU()
   {
      return this.purchasePricePU;
   }

   public void setPurchasePricePU(final BigDecimal purchasePricePU)
   {
      this.purchasePricePU = purchasePricePU;
   }

   public BigDecimal getSalesPricePU()
   {
      return this.salesPricePU;
   }

   public void setSalesPricePU(final BigDecimal salesPricePU)
   {
      this.salesPricePU = salesPricePU;
   }

   public BigDecimal getAmountReturn()
   {
      return this.amountReturn;
   }

   public void setAmountReturn(final BigDecimal amountReturn)
   {
      this.amountReturn = amountReturn;
   }

   public BigDecimal getTotalSalesPrice()
   {
      return this.totalSalesPrice;
   }

   public void setTotalSalesPrice(final BigDecimal totalSalesPrice)
   {
      this.totalSalesPrice = totalSalesPrice;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (internalPic != null && !internalPic.trim().isEmpty())
         result += "internalPic: " + internalPic;
      if (indexLine != null)
         result += ", indexLine: " + indexLine;
      return result;
   }

   public SupplierInvoice getInvoice()
   {
      return this.invoice;
   }

   public void setInvoice(final SupplierInvoice invoice)
   {
      this.invoice = invoice;
   }
}