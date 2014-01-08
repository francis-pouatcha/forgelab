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
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.Site;
import javax.persistence.ManyToOne;
import org.adorsys.adph.server.jpa.PharmaUser;
import org.adorsys.adph.server.jpa.CashDrawer;
import org.adorsys.adph.server.jpa.Invoice;
import org.adorsys.adph.server.jpa.PaymentType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.Payment.description")
@Audited
@XmlRootElement
public class Payment implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.paymentNumber.description")
   private String paymentNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.Payment.paymentDate.description")
   private Date paymentDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.Payment.recordDate.description")
   private Date recordDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.amount.description")
   private BigDecimal amount;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.receivedAmount.description")
   private BigDecimal receivedAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.difference.description")
   private BigDecimal difference;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Payment.site.description")
   private Site site;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Payment.cashier.description")
   private PharmaUser cashier;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Payment.cashDrawer.description")
   private CashDrawer cashDrawer;

   @ManyToOne
   private Invoice invoice;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.paymentType.description")
   @Enumerated
   private PaymentType paymentType;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.paymentReceiptPrinted.description")
   private boolean paymentReceiptPrinted;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.paidBy.description")
   private String paidBy;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.clientName.description")
   private String clientName;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.note.description")
   @Size(max = 256)
   private String note;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.voucherNumber.description")
   private String voucherNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.voucherAmount.description")
   private BigDecimal voucherAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.Payment.compensation.description")
   private boolean compensation;

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
         return id.equals(((Payment) that).id);
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

   public String getPaymentNumber()
   {
      return this.paymentNumber;
   }

   public void setPaymentNumber(final String paymentNumber)
   {
      this.paymentNumber = paymentNumber;
   }

   public Date getPaymentDate()
   {
      return this.paymentDate;
   }

   public void setPaymentDate(final Date paymentDate)
   {
      this.paymentDate = paymentDate;
   }

   public Date getRecordDate()
   {
      return this.recordDate;
   }

   public void setRecordDate(final Date recordDate)
   {
      this.recordDate = recordDate;
   }

   public BigDecimal getAmount()
   {
      return this.amount;
   }

   public void setAmount(final BigDecimal amount)
   {
      this.amount = amount;
   }

   public BigDecimal getReceivedAmount()
   {
      return this.receivedAmount;
   }

   public void setReceivedAmount(final BigDecimal receivedAmount)
   {
      this.receivedAmount = receivedAmount;
   }

   public BigDecimal getDifference()
   {
      return this.difference;
   }

   public void setDifference(final BigDecimal difference)
   {
      this.difference = difference;
   }

   public Site getSite()
   {
      return this.site;
   }

   public void setSite(final Site site)
   {
      this.site = site;
   }

   public PharmaUser getCashier()
   {
      return this.cashier;
   }

   public void setCashier(final PharmaUser cashier)
   {
      this.cashier = cashier;
   }

   public CashDrawer getCashDrawer()
   {
      return this.cashDrawer;
   }

   public void setCashDrawer(final CashDrawer cashDrawer)
   {
      this.cashDrawer = cashDrawer;
   }

   public Invoice getInvoice()
   {
      return this.invoice;
   }

   public void setInvoice(final Invoice invoice)
   {
      this.invoice = invoice;
   }

   public PaymentType getPaymentType()
   {
      return this.paymentType;
   }

   public void setPaymentType(final PaymentType paymentType)
   {
      this.paymentType = paymentType;
   }

   public boolean getPaymentReceiptPrinted()
   {
      return this.paymentReceiptPrinted;
   }

   public void setPaymentReceiptPrinted(final boolean paymentReceiptPrinted)
   {
      this.paymentReceiptPrinted = paymentReceiptPrinted;
   }

   public String getPaidBy()
   {
      return this.paidBy;
   }

   public void setPaidBy(final String paidBy)
   {
      this.paidBy = paidBy;
   }

   public String getClientName()
   {
      return this.clientName;
   }

   public void setClientName(final String clientName)
   {
      this.clientName = clientName;
   }

   public String getNote()
   {
      return this.note;
   }

   public void setNote(final String note)
   {
      this.note = note;
   }

   public String getVoucherNumber()
   {
      return this.voucherNumber;
   }

   public void setVoucherNumber(final String voucherNumber)
   {
      this.voucherNumber = voucherNumber;
   }

   public BigDecimal getVoucherAmount()
   {
      return this.voucherAmount;
   }

   public void setVoucherAmount(final BigDecimal voucherAmount)
   {
      this.voucherAmount = voucherAmount;
   }

   public boolean getCompensation()
   {
      return this.compensation;
   }

   public void setCompensation(final boolean compensation)
   {
      this.compensation = compensation;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (paymentNumber != null && !paymentNumber.trim().isEmpty())
         result += "paymentNumber: " + paymentNumber;
      result += ", paymentReceiptPrinted: " + paymentReceiptPrinted;
      if (paidBy != null && !paidBy.trim().isEmpty())
         result += ", paidBy: " + paidBy;
      if (clientName != null && !clientName.trim().isEmpty())
         result += ", clientName: " + clientName;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      if (voucherNumber != null && !voucherNumber.trim().isEmpty())
         result += ", voucherNumber: " + voucherNumber;
      result += ", compensation: " + compensation;
      return result;
   }
}