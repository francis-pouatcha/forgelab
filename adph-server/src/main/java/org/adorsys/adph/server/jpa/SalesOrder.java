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
import org.adorsys.adph.server.jpa.Client;
import javax.persistence.ManyToOne;
import org.adorsys.adph.server.jpa.PharmaUser;
import org.adorsys.adph.server.jpa.DocumentProcessingState;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.SalesOrderType;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.SalesOrder.description")
@Audited
@XmlRootElement
public class SalesOrder implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.soNumber.description")
   private String soNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.SalesOrder.creationDate.description")
   private Date creationDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.SalesOrder.cancelationDate.description")
   private Date cancelationDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.SalesOrder.restorationDate.description")
   private Date restorationDate;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.SalesOrder.client.description")
   private Client client;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.SalesOrder.salesStaff.description")
   private PharmaUser salesStaff;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.salesOrderStatus.description")
   @Enumerated
   private DocumentProcessingState salesOrderStatus;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.cashed.description")
   private boolean cashed;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.canceled.description")
   private boolean canceled;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.amountBeforeTax.description")
   private BigDecimal amountBeforeTax;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.amountVAT.description")
   private BigDecimal amountVAT;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.amountDiscount.description")
   private BigDecimal amountDiscount;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.amountAfterTax.description")
   private BigDecimal amountAfterTax;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.otherDiscount.description")
   private BigDecimal otherDiscount;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.invoiceId.description")
   private long invoiceId;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.salesOrderType.description")
   @Enumerated
   private SalesOrderType salesOrderType;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.SalesOrder.payingClient.description")
   @NotNull
   private Client payingClient;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.coverageRate.description")
   private BigDecimal coverageRate;

   @Column
   private boolean partialSales;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.voucherNumber.description")
   private String voucherNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.SalesOrder.couponNumber.description")
   private String couponNumber;

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
         return id.equals(((SalesOrder) that).id);
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

   public String getSoNumber()
   {
      return this.soNumber;
   }

   public void setSoNumber(final String soNumber)
   {
      this.soNumber = soNumber;
   }

   public Date getCreationDate()
   {
      return this.creationDate;
   }

   public void setCreationDate(final Date creationDate)
   {
      this.creationDate = creationDate;
   }

   public Date getCancelationDate()
   {
      return this.cancelationDate;
   }

   public void setCancelationDate(final Date cancelationDate)
   {
      this.cancelationDate = cancelationDate;
   }

   public Date getRestorationDate()
   {
      return this.restorationDate;
   }

   public void setRestorationDate(final Date restorationDate)
   {
      this.restorationDate = restorationDate;
   }

   public Client getClient()
   {
      return this.client;
   }

   public void setClient(final Client client)
   {
      this.client = client;
   }

   public PharmaUser getSalesStaff()
   {
      return this.salesStaff;
   }

   public void setSalesStaff(final PharmaUser salesStaff)
   {
      this.salesStaff = salesStaff;
   }

   public DocumentProcessingState getSalesOrderStatus()
   {
      return this.salesOrderStatus;
   }

   public void setSalesOrderStatus(final DocumentProcessingState salesOrderStatus)
   {
      this.salesOrderStatus = salesOrderStatus;
   }

   public boolean getCashed()
   {
      return this.cashed;
   }

   public void setCashed(final boolean cashed)
   {
      this.cashed = cashed;
   }

   public boolean getCanceled()
   {
      return this.canceled;
   }

   public void setCanceled(final boolean canceled)
   {
      this.canceled = canceled;
   }

   public BigDecimal getAmountBeforeTax()
   {
      return this.amountBeforeTax;
   }

   public void setAmountBeforeTax(final BigDecimal amountBeforeTax)
   {
      this.amountBeforeTax = amountBeforeTax;
   }

   public BigDecimal getAmountVAT()
   {
      return this.amountVAT;
   }

   public void setAmountVAT(final BigDecimal amountVAT)
   {
      this.amountVAT = amountVAT;
   }

   public BigDecimal getAmountDiscount()
   {
      return this.amountDiscount;
   }

   public void setAmountDiscount(final BigDecimal amountDiscount)
   {
      this.amountDiscount = amountDiscount;
   }

   public BigDecimal getAmountAfterTax()
   {
      return this.amountAfterTax;
   }

   public void setAmountAfterTax(final BigDecimal amountAfterTax)
   {
      this.amountAfterTax = amountAfterTax;
   }

   public BigDecimal getOtherDiscount()
   {
      return this.otherDiscount;
   }

   public void setOtherDiscount(final BigDecimal otherDiscount)
   {
      this.otherDiscount = otherDiscount;
   }

   public long getInvoiceId()
   {
      return this.invoiceId;
   }

   public void setInvoiceId(final long invoiceId)
   {
      this.invoiceId = invoiceId;
   }

   public SalesOrderType getSalesOrderType()
   {
      return this.salesOrderType;
   }

   public void setSalesOrderType(final SalesOrderType salesOrderType)
   {
      this.salesOrderType = salesOrderType;
   }

   public Client getPayingClient()
   {
      return this.payingClient;
   }

   public void setPayingClient(final Client payingClient)
   {
      this.payingClient = payingClient;
   }

   public BigDecimal getCoverageRate()
   {
      return this.coverageRate;
   }

   public void setCoverageRate(final BigDecimal coverageRate)
   {
      this.coverageRate = coverageRate;
   }

   public boolean getPartialSales()
   {
      return this.partialSales;
   }

   public void setPartialSales(final boolean partialSales)
   {
      this.partialSales = partialSales;
   }

   public String getVoucherNumber()
   {
      return this.voucherNumber;
   }

   public void setVoucherNumber(final String voucherNumber)
   {
      this.voucherNumber = voucherNumber;
   }

   public String getCouponNumber()
   {
      return this.couponNumber;
   }

   public void setCouponNumber(final String couponNumber)
   {
      this.couponNumber = couponNumber;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (soNumber != null && !soNumber.trim().isEmpty())
         result += "soNumber: " + soNumber;
      result += ", cashed: " + cashed;
      result += ", canceled: " + canceled;
      result += ", invoiceId: " + invoiceId;
      result += ", partialSales: " + partialSales;
      if (voucherNumber != null && !voucherNumber.trim().isEmpty())
         result += ", voucherNumber: " + voucherNumber;
      if (couponNumber != null && !couponNumber.trim().isEmpty())
         result += ", couponNumber: " + couponNumber;
      return result;
   }
}