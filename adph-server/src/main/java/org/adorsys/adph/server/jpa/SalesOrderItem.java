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
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.adph.server.jpa.PurchaseOrderItem;
import javax.persistence.ManyToOne;
import org.adorsys.adph.server.jpa.SalesOrder;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.SalesOrderItem.description")
@Audited
@XmlRootElement
public class SalesOrderItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.soItemNumber.description")
   private String soItemNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.name.description")
   private String name;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.orderedQty.description")
   private BigDecimal orderedQty;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.returnedQty.description")
   private BigDecimal returnedQty;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.recordDate.description")
   private Date recordDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.salesPricePU.description")
   private BigDecimal salesPricePU;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.unitDiscount.description")
   private BigDecimal unitDiscount;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.totalDiscount.description")
   private BigDecimal totalDiscount;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.pic.description")
   private String pic;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.picm.description")
   private String picm;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.designation.description")
   private String designation;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.modifyingUser.description")
   private String modifyingUser;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.purchaseOrderItem.description")
   private PurchaseOrderItem purchaseOrderItem;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.SalesOrderItem.salesOrder.description")
   private SalesOrder salesOrder;

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
         return id.equals(((SalesOrderItem) that).id);
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

   public String getSoItemNumber()
   {
      return this.soItemNumber;
   }

   public void setSoItemNumber(final String soItemNumber)
   {
      this.soItemNumber = soItemNumber;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public BigDecimal getOrderedQty()
   {
      return this.orderedQty;
   }

   public void setOrderedQty(final BigDecimal orderedQty)
   {
      this.orderedQty = orderedQty;
   }

   public BigDecimal getReturnedQty()
   {
      return this.returnedQty;
   }

   public void setReturnedQty(final BigDecimal returnedQty)
   {
      this.returnedQty = returnedQty;
   }

   public Date getRecordDate()
   {
      return this.recordDate;
   }

   public void setRecordDate(final Date recordDate)
   {
      this.recordDate = recordDate;
   }

   public BigDecimal getSalesPricePU()
   {
      return this.salesPricePU;
   }

   public void setSalesPricePU(final BigDecimal salesPricePU)
   {
      this.salesPricePU = salesPricePU;
   }

   public BigDecimal getUnitDiscount()
   {
      return this.unitDiscount;
   }

   public void setUnitDiscount(final BigDecimal unitDiscount)
   {
      this.unitDiscount = unitDiscount;
   }

   public BigDecimal getTotalDiscount()
   {
      return this.totalDiscount;
   }

   public void setTotalDiscount(final BigDecimal totalDiscount)
   {
      this.totalDiscount = totalDiscount;
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

   public String getModifyingUser()
   {
      return this.modifyingUser;
   }

   public void setModifyingUser(final String modifyingUser)
   {
      this.modifyingUser = modifyingUser;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (soItemNumber != null && !soItemNumber.trim().isEmpty())
         result += "soItemNumber: " + soItemNumber;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (pic != null && !pic.trim().isEmpty())
         result += ", pic: " + pic;
      if (picm != null && !picm.trim().isEmpty())
         result += ", picm: " + picm;
      if (designation != null && !designation.trim().isEmpty())
         result += ", designation: " + designation;
      if (modifyingUser != null && !modifyingUser.trim().isEmpty())
         result += ", modifyingUser: " + modifyingUser;
      return result;
   }

   public PurchaseOrderItem getPurchaseOrderItem()
   {
      return this.purchaseOrderItem;
   }

   public void setPurchaseOrderItem(final PurchaseOrderItem purchaseOrderItem)
   {
      this.purchaseOrderItem = purchaseOrderItem;
   }

   public SalesOrder getSalesOrder()
   {
      return this.salesOrder;
   }

   public void setSalesOrder(final SalesOrder salesOrder)
   {
      this.salesOrder = salesOrder;
   }
}