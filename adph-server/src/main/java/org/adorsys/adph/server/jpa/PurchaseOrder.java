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
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.adph.server.jpa.PharmaUser;
import javax.persistence.ManyToOne;
import org.adorsys.adph.server.jpa.Site;
import org.adorsys.adph.server.jpa.Currency;
import org.adorsys.adph.server.jpa.Supplier;
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.VAT;
import org.adorsys.adph.server.jpa.DocumentProcessingState;
import javax.persistence.Enumerated;
import org.adorsys.adph.server.jpa.Agency;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.PurchaseOrder.description")
@Audited
@XmlRootElement
public class PurchaseOrder implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.poNumber.description")
   private String poNumber;

   @Column
   @NotNull
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.deliverySlipNumber.description")
   private String deliverySlipNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.dateOnDeliverySlip.description")
   private Date dateOnDeliverySlip;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.creatingUser.description")
   @NotNull
   private PharmaUser creatingUser;

   @ManyToOne
   @NotNull
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.site.description")
   private Site site;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.currency.description")
   private Currency currency;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.orderDate.description")
   private Date orderDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.deliveryDate.description")
   private Date deliveryDate;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.supplier.description")
   private Supplier supplier;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.paymentDate.description")
   private Date paymentDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.amountBeforeTax.description")
   @NotNull
   private BigDecimal amountBeforeTax;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.amountAfterTax.description")
   private BigDecimal amountAfterTax;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.amountDiscount.description")
   private BigDecimal amountDiscount;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.netAmountToPay.description")
   private BigDecimal netAmountToPay;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.vat.description")
   private VAT vat;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.creationDate.description")
   private Date creationDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.claims.description")
   private boolean claims;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.emergency.description")
   private boolean emergency;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.closed.description")
   private boolean closed;

   @Column
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.orderProcessingState.description")
   @Enumerated
   private DocumentProcessingState orderProcessingState;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.PurchaseOrder.receivingAgency.description")
   private Agency receivingAgency;

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
         return id.equals(((PurchaseOrder) that).id);
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

   public String getPoNumber()
   {
      return this.poNumber;
   }

   public void setPoNumber(final String poNumber)
   {
      this.poNumber = poNumber;
   }

   public String getDeliverySlipNumber()
   {
      return this.deliverySlipNumber;
   }

   public void setDeliverySlipNumber(final String deliverySlipNumber)
   {
      this.deliverySlipNumber = deliverySlipNumber;
   }

   public Date getDateOnDeliverySlip()
   {
      return this.dateOnDeliverySlip;
   }

   public void setDateOnDeliverySlip(final Date dateOnDeliverySlip)
   {
      this.dateOnDeliverySlip = dateOnDeliverySlip;
   }

   public PharmaUser getCreatingUser()
   {
      return this.creatingUser;
   }

   public void setCreatingUser(final PharmaUser creatingUser)
   {
      this.creatingUser = creatingUser;
   }

   public Site getSite()
   {
      return this.site;
   }

   public void setSite(final Site site)
   {
      this.site = site;
   }

   public Currency getCurrency()
   {
      return this.currency;
   }

   public void setCurrency(final Currency currency)
   {
      this.currency = currency;
   }

   public Date getOrderDate()
   {
      return this.orderDate;
   }

   public void setOrderDate(final Date orderDate)
   {
      this.orderDate = orderDate;
   }

   public Date getDeliveryDate()
   {
      return this.deliveryDate;
   }

   public void setDeliveryDate(final Date deliveryDate)
   {
      this.deliveryDate = deliveryDate;
   }

   public Supplier getSupplier()
   {
      return this.supplier;
   }

   public void setSupplier(final Supplier supplier)
   {
      this.supplier = supplier;
   }

   public Date getPaymentDate()
   {
      return this.paymentDate;
   }

   public void setPaymentDate(final Date paymentDate)
   {
      this.paymentDate = paymentDate;
   }

   public BigDecimal getAmountBeforeTax()
   {
      return this.amountBeforeTax;
   }

   public void setAmountBeforeTax(final BigDecimal amountBeforeTax)
   {
      this.amountBeforeTax = amountBeforeTax;
   }

   public BigDecimal getAmountAfterTax()
   {
      return this.amountAfterTax;
   }

   public void setAmountAfterTax(final BigDecimal amountAfterTax)
   {
      this.amountAfterTax = amountAfterTax;
   }

   public BigDecimal getAmountDiscount()
   {
      return this.amountDiscount;
   }

   public void setAmountDiscount(final BigDecimal amountDiscount)
   {
      this.amountDiscount = amountDiscount;
   }

   public BigDecimal getNetAmountToPay()
   {
      return this.netAmountToPay;
   }

   public void setNetAmountToPay(final BigDecimal netAmountToPay)
   {
      this.netAmountToPay = netAmountToPay;
   }

   public VAT getVat()
   {
      return this.vat;
   }

   public void setVat(final VAT vat)
   {
      this.vat = vat;
   }

   public Date getCreationDate()
   {
      return this.creationDate;
   }

   public void setCreationDate(final Date creationDate)
   {
      this.creationDate = creationDate;
   }

   public boolean getClaims()
   {
      return this.claims;
   }

   public void setClaims(final boolean claims)
   {
      this.claims = claims;
   }

   public boolean getEmergency()
   {
      return this.emergency;
   }

   public void setEmergency(final boolean emergency)
   {
      this.emergency = emergency;
   }

   public boolean getClosed()
   {
      return this.closed;
   }

   public void setClosed(final boolean closed)
   {
      this.closed = closed;
   }

   public DocumentProcessingState getOrderProcessingState()
   {
      return this.orderProcessingState;
   }

   public void setOrderProcessingState(
         final DocumentProcessingState orderProcessingState)
   {
      this.orderProcessingState = orderProcessingState;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (poNumber != null && !poNumber.trim().isEmpty())
         result += "poNumber: " + poNumber;
      if (deliverySlipNumber != null && !deliverySlipNumber.trim().isEmpty())
         result += ", deliverySlipNumber: " + deliverySlipNumber;
      result += ", claims: " + claims;
      result += ", emergency: " + emergency;
      result += ", closed: " + closed;
      return result;
   }

   public Agency getReceivingAgency()
   {
      return this.receivingAgency;
   }

   public void setReceivingAgency(final Agency receivingAgency)
   {
      this.receivingAgency = receivingAgency;
   }
}