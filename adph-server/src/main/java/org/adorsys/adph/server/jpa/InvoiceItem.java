package org.adorsys.adph.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.Invoice;
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.InvoiceItem.description")
@Audited
@XmlRootElement
public class InvoiceItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.indexLine.description")
   private int indexLine;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.pic.description")
   private String pic;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.picm.description")
   private String picm;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.designation.description")
   private String designation;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.purchasedQty.description")
   private BigDecimal purchasedQty;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.returnedQty.description")
   private BigDecimal returnedQty;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.salesPricePU.description")
   private BigDecimal salesPricePU;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.discountAmount.description")
   private BigDecimal discountAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.InvoiceItem.totalSalesPrice.description")
   private BigDecimal totalSalesPrice;

   @ManyToOne
   private Invoice invoice;

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
         return id.equals(((InvoiceItem) that).id);
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

   public int getIndexLine()
   {
      return this.indexLine;
   }

   public void setIndexLine(final int indexLine)
   {
      this.indexLine = indexLine;
   }

   public String getPic()
   {
      return this.pic;
   }

   public void setPic(final String pic)
   {
      this.pic = pic;
   }

   public String getPicm()
   {
      return this.picm;
   }

   public void setPicm(final String picm)
   {
      this.picm = picm;
   }

   public String getDesignation()
   {
      return this.designation;
   }

   public void setDesignation(final String designation)
   {
      this.designation = designation;
   }

   public BigDecimal getPurchasedQty()
   {
      return this.purchasedQty;
   }

   public void setPurchasedQty(final BigDecimal purchasedQty)
   {
      this.purchasedQty = purchasedQty;
   }

   public BigDecimal getReturnedQty()
   {
      return this.returnedQty;
   }

   public void setReturnedQty(final BigDecimal returnedQty)
   {
      this.returnedQty = returnedQty;
   }

   public BigDecimal getSalesPricePU()
   {
      return this.salesPricePU;
   }

   public void setSalesPricePU(final BigDecimal salesPricePU)
   {
      this.salesPricePU = salesPricePU;
   }

   public BigDecimal getDiscountAmount()
   {
      return this.discountAmount;
   }

   public void setDiscountAmount(final BigDecimal discountAmount)
   {
      this.discountAmount = discountAmount;
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
      result += "indexLine: " + indexLine;
      if (pic != null && !pic.trim().isEmpty())
         result += ", pic: " + pic;
      if (picm != null && !picm.trim().isEmpty())
         result += ", picm: " + picm;
      if (designation != null && !designation.trim().isEmpty())
         result += ", designation: " + designation;
      return result;
   }

   public Invoice getInvoice()
   {
      return this.invoice;
   }

   public void setInvoice(final Invoice invoice)
   {
      this.invoice = invoice;
   }
}