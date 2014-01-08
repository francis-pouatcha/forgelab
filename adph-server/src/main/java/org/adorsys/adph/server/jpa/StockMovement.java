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
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.adph.server.jpa.PharmaUser;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.StockMovementType;
import javax.persistence.Enumerated;
import org.adorsys.adph.server.jpa.Site;
import org.adorsys.adph.server.jpa.Agency;
import javax.validation.constraints.Size;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.StockMovement.description")
@Audited
@XmlRootElement
public class StockMovement implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.mvtNumber.description")
   private String mvtNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.StockMovement.creationDate.description")
   private Date creationDate;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.StockMovement.creatingUser.description")
   @NotNull
   private PharmaUser creatingUser;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.movedQty.description")
   private BigDecimal movedQty;

   @Column
   @NotNull
   @Description("org.adorsys.adph.server.jpa.StockMovement.movementType.description")
   @Enumerated
   private StockMovementType movementType;

   @ManyToOne
   @NotNull
   @Description("org.adorsys.adph.server.jpa.StockMovement.site.description")
   private Site site;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.movementOrigin.description")
   @Enumerated
   private StockMovementType movementOrigin;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.movementDestination.description")
   @Enumerated
   private StockMovementType movementDestination;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.soNumber.description")
   private String soNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.deliverySlipNumber.description")
   private String deliverySlipNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.pic.description")
   private String pic;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.picm.description")
   private String picm;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.StockMovement.agency.description")
   private Agency agency;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.designation.description")
   private String designation;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.initialQty.description")
   private BigDecimal initialQty;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.finalQty.description")
   private BigDecimal finalQty;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.cashDrawerNumber.description")
   private String cashDrawerNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.totalPurchasingPrice.description")
   @NotNull
   private BigDecimal totalPurchasingPrice;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.totalDiscount.description")
   private BigDecimal totalDiscount;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.totalSalesPrice.description")
   private BigDecimal totalSalesPrice;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.note.description")
   @Size(max = 256)
   private String note;

   @Column
   @Description("org.adorsys.adph.server.jpa.StockMovement.canceled.description")
   private boolean canceled;

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
         return id.equals(((StockMovement) that).id);
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

   public String getMvtNumber()
   {
      return this.mvtNumber;
   }

   public void setMvtNumber(final String mvtNumber)
   {
      this.mvtNumber = mvtNumber;
   }

   public Date getCreationDate()
   {
      return this.creationDate;
   }

   public void setCreationDate(final Date creationDate)
   {
      this.creationDate = creationDate;
   }

   public PharmaUser getCreatingUser()
   {
      return this.creatingUser;
   }

   public void setCreatingUser(final PharmaUser creatingUser)
   {
      this.creatingUser = creatingUser;
   }

   public BigDecimal getMovedQty()
   {
      return this.movedQty;
   }

   public void setMovedQty(final BigDecimal movedQty)
   {
      this.movedQty = movedQty;
   }

   public StockMovementType getMovementType()
   {
      return this.movementType;
   }

   public void setMovementType(final StockMovementType movementType)
   {
      this.movementType = movementType;
   }

   public Site getSite()
   {
      return this.site;
   }

   public void setSite(final Site site)
   {
      this.site = site;
   }

   public StockMovementType getMovementOrigin()
   {
      return this.movementOrigin;
   }

   public void setMovementOrigin(final StockMovementType movementOrigin)
   {
      this.movementOrigin = movementOrigin;
   }

   public StockMovementType getMovementDestination()
   {
      return this.movementDestination;
   }

   public void setMovementDestination(final StockMovementType movementDestination)
   {
      this.movementDestination = movementDestination;
   }

   public String getSoNumber()
   {
      return this.soNumber;
   }

   public void setSoNumber(final String soNumber)
   {
      this.soNumber = soNumber;
   }

   public String getDeliverySlipNumber()
   {
      return this.deliverySlipNumber;
   }

   public void setDeliverySlipNumber(final String deliverySlipNumber)
   {
      this.deliverySlipNumber = deliverySlipNumber;
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

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
   }

   public String getDesignation()
   {
      return this.designation;
   }

   public void setDesignation(final String designation)
   {
      this.designation = designation;
   }

   public BigDecimal getInitialQty()
   {
      return this.initialQty;
   }

   public void setInitialQty(final BigDecimal initialQty)
   {
      this.initialQty = initialQty;
   }

   public BigDecimal getFinalQty()
   {
      return this.finalQty;
   }

   public void setFinalQty(final BigDecimal finalQty)
   {
      this.finalQty = finalQty;
   }

   public String getCashDrawerNumber()
   {
      return this.cashDrawerNumber;
   }

   public void setCashDrawerNumber(final String cashDrawerNumber)
   {
      this.cashDrawerNumber = cashDrawerNumber;
   }

   public BigDecimal getTotalPurchasingPrice()
   {
      return this.totalPurchasingPrice;
   }

   public void setTotalPurchasingPrice(final BigDecimal totalPurchasingPrice)
   {
      this.totalPurchasingPrice = totalPurchasingPrice;
   }

   public BigDecimal getTotalDiscount()
   {
      return this.totalDiscount;
   }

   public void setTotalDiscount(final BigDecimal totalDiscount)
   {
      this.totalDiscount = totalDiscount;
   }

   public BigDecimal getTotalSalesPrice()
   {
      return this.totalSalesPrice;
   }

   public void setTotalSalesPrice(final BigDecimal totalSalesPrice)
   {
      this.totalSalesPrice = totalSalesPrice;
   }

   public String getNote()
   {
      return this.note;
   }

   public void setNote(final String note)
   {
      this.note = note;
   }

   public boolean getCanceled()
   {
      return this.canceled;
   }

   public void setCanceled(final boolean canceled)
   {
      this.canceled = canceled;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (mvtNumber != null && !mvtNumber.trim().isEmpty())
         result += "mvtNumber: " + mvtNumber;
      if (soNumber != null && !soNumber.trim().isEmpty())
         result += ", soNumber: " + soNumber;
      if (deliverySlipNumber != null && !deliverySlipNumber.trim().isEmpty())
         result += ", deliverySlipNumber: " + deliverySlipNumber;
      if (pic != null && !pic.trim().isEmpty())
         result += ", pic: " + pic;
      if (picm != null && !picm.trim().isEmpty())
         result += ", picm: " + picm;
      if (designation != null && !designation.trim().isEmpty())
         result += ", designation: " + designation;
      if (cashDrawerNumber != null && !cashDrawerNumber.trim().isEmpty())
         result += ", cashDrawerNumber: " + cashDrawerNumber;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      result += ", canceled: " + canceled;
      return result;
   }
}