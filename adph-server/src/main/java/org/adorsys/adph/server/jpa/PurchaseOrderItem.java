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
import org.adorsys.adph.server.jpa.PurchaseOrder;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import org.adorsys.adph.server.jpa.Article;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.description")
@Audited
@XmlRootElement
public class PurchaseOrderItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.purchaseOrder.description")
   private PurchaseOrder purchaseOrder;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.poItemNumber.description")
   private String poItemNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.lineIndex.description")
   private String lineIndex;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.pic.description")
   private String pic;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.localPic.description")
   @Size(min = 7)
   private String localPic;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.article.description")
   private Article article;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.designation.description")
   private String designation;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.productionDate.description")
   private Date productionDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.expirationDate.description")
   private Date expirationDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.modifyingUser.description")
   private String modifyingUser;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.productRecCreated.description")
   private Date productRecCreated;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.quantity.description")
   private BigDecimal quantity;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.freeQuantity.description")
   private BigDecimal freeQuantity;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.soldQuantity.description")
   private BigDecimal soldQuantity;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.claimedQuantity.description")
   private BigDecimal claimedQuantity;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.stockQuantity.description")
   private BigDecimal stockQuantity;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.releasedQuantity.description")
   private BigDecimal releasedQuantity;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.salesPricePU.description")
   private BigDecimal salesPricePU;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.purchasePricePU.description")
   private BigDecimal purchasePricePU;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.totalPurchasePrice.description")
   private BigDecimal totalPurchasePrice;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.grossMargin.description")
   private BigDecimal grossMargin;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.approvedForSale.description")
   private boolean approvedForSale;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.maxDiscountRate.description")
   private BigDecimal maxDiscountRate;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrderItem.discountApproved.description")
   private boolean discountApproved;

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
         return id.equals(((PurchaseOrderItem) that).id);
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

   public PurchaseOrder getPurchaseOrder()
   {
      return this.purchaseOrder;
   }

   public void setPurchaseOrder(final PurchaseOrder purchaseOrder)
   {
      this.purchaseOrder = purchaseOrder;
   }

   public String getPoItemNumber()
   {
      return this.poItemNumber;
   }

   public void setPoItemNumber(final String poItemNumber)
   {
      this.poItemNumber = poItemNumber;
   }

   public String getLineIndex()
   {
      return this.lineIndex;
   }

   public void setLineIndex(final String lineIndex)
   {
      this.lineIndex = lineIndex;
   }

   public String getPic()
   {
      return this.pic;
   }

   public void setPic(final String pic)
   {
      this.pic = pic;
   }

   public String getLocalPic()
   {
      return this.localPic;
   }

   public void setLocalPic(final String localPic)
   {
      this.localPic = localPic;
   }

   public Article getArticle()
   {
      return this.article;
   }

   public void setArticle(final Article article)
   {
      this.article = article;
   }

   public String getDesignation()
   {
      return this.designation;
   }

   public void setDesignation(final String designation)
   {
      this.designation = designation;
   }

   public Date getProductionDate()
   {
      return this.productionDate;
   }

   public void setProductionDate(final Date productionDate)
   {
      this.productionDate = productionDate;
   }

   public Date getExpirationDate()
   {
      return this.expirationDate;
   }

   public void setExpirationDate(final Date expirationDate)
   {
      this.expirationDate = expirationDate;
   }

   public String getModifyingUser()
   {
      return this.modifyingUser;
   }

   public void setModifyingUser(final String modifyingUser)
   {
      this.modifyingUser = modifyingUser;
   }

   public Date getProductRecCreated()
   {
      return this.productRecCreated;
   }

   public void setProductRecCreated(final Date productRecCreated)
   {
      this.productRecCreated = productRecCreated;
   }

   public BigDecimal getQuantity()
   {
      return this.quantity;
   }

   public void setQuantity(final BigDecimal quantity)
   {
      this.quantity = quantity;
   }

   public BigDecimal getFreeQuantity()
   {
      return this.freeQuantity;
   }

   public void setFreeQuantity(final BigDecimal freeQuantity)
   {
      this.freeQuantity = freeQuantity;
   }

   public BigDecimal getSoldQuantity()
   {
      return this.soldQuantity;
   }

   public void setSoldQuantity(final BigDecimal soldQuantity)
   {
      this.soldQuantity = soldQuantity;
   }

   public BigDecimal getClaimedQuantity()
   {
      return this.claimedQuantity;
   }

   public void setClaimedQuantity(final BigDecimal claimedQuantity)
   {
      this.claimedQuantity = claimedQuantity;
   }

   public BigDecimal getStockQuantity()
   {
      return this.stockQuantity;
   }

   public void setStockQuantity(final BigDecimal stockQuantity)
   {
      this.stockQuantity = stockQuantity;
   }

   public BigDecimal getReleasedQuantity()
   {
      return this.releasedQuantity;
   }

   public void setReleasedQuantity(final BigDecimal releasedQuantity)
   {
      this.releasedQuantity = releasedQuantity;
   }

   public BigDecimal getSalesPricePU()
   {
      return this.salesPricePU;
   }

   public void setSalesPricePU(final BigDecimal salesPricePU)
   {
      this.salesPricePU = salesPricePU;
   }

   public BigDecimal getPurchasePricePU()
   {
      return this.purchasePricePU;
   }

   public void setPurchasePricePU(final BigDecimal purchasePricePU)
   {
      this.purchasePricePU = purchasePricePU;
   }

   public BigDecimal getTotalPurchasePrice()
   {
      return this.totalPurchasePrice;
   }

   public void setTotalPurchasePrice(final BigDecimal totalPurchasePrice)
   {
      this.totalPurchasePrice = totalPurchasePrice;
   }

   public BigDecimal getGrossMargin()
   {
      return this.grossMargin;
   }

   public void setGrossMargin(final BigDecimal grossMargin)
   {
      this.grossMargin = grossMargin;
   }

   public boolean getApprovedForSale()
   {
      return this.approvedForSale;
   }

   public void setApprovedForSale(final boolean approvedForSale)
   {
      this.approvedForSale = approvedForSale;
   }

   public BigDecimal getMaxDiscountRate()
   {
      return this.maxDiscountRate;
   }

   public void setMaxDiscountRate(final BigDecimal maxDiscountRate)
   {
      this.maxDiscountRate = maxDiscountRate;
   }

   public boolean getDiscountApproved()
   {
      return this.discountApproved;
   }

   public void setDiscountApproved(final boolean discountApproved)
   {
      this.discountApproved = discountApproved;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (poItemNumber != null && !poItemNumber.trim().isEmpty())
         result += "poItemNumber: " + poItemNumber;
      if (lineIndex != null && !lineIndex.trim().isEmpty())
         result += ", lineIndex: " + lineIndex;
      if (pic != null && !pic.trim().isEmpty())
         result += ", pic: " + pic;
      if (localPic != null && !localPic.trim().isEmpty())
         result += ", localPic: " + localPic;
      if (designation != null && !designation.trim().isEmpty())
         result += ", designation: " + designation;
      if (modifyingUser != null && !modifyingUser.trim().isEmpty())
         result += ", modifyingUser: " + modifyingUser;
      result += ", approvedForSale: " + approvedForSale;
      result += ", discountApproved: " + discountApproved;
      return result;
   }
}