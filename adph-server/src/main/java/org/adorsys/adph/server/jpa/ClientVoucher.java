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
import javax.validation.constraints.NotNull;
import org.adorsys.adph.server.jpa.VoucherType;
import javax.persistence.Enumerated;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.ClientVoucher.description")
@Audited
@XmlRootElement
public class ClientVoucher implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.voucherNumber.description")
   private String voucherNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.modifDate.description")
   private Date modifDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.amount.description")
   @NotNull
   private BigDecimal amount;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.clientName.description")
   private String clientName;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.clientNumber.description")
   private String clientNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.canceled.description")
   private boolean canceled;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.recordingUser.description")
   private String recordingUser;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.modifiedDate.description")
   private Date modifiedDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.voucherType.description")
   @Enumerated
   private VoucherType voucherType;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.amountUsed.description")
   private BigDecimal amountUsed;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.settled.description")
   private boolean settled;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.restAmount.description")
   private BigDecimal restAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientVoucher.voucherPrinted.description")
   private boolean voucherPrinted;

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
         return id.equals(((ClientVoucher) that).id);
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

   public String getVoucherNumber()
   {
      return this.voucherNumber;
   }

   public void setVoucherNumber(final String voucherNumber)
   {
      this.voucherNumber = voucherNumber;
   }

   public Date getModifDate()
   {
      return this.modifDate;
   }

   public void setModifDate(final Date modifDate)
   {
      this.modifDate = modifDate;
   }

   public BigDecimal getAmount()
   {
      return this.amount;
   }

   public void setAmount(final BigDecimal amount)
   {
      this.amount = amount;
   }

   public String getClientName()
   {
      return this.clientName;
   }

   public void setClientName(final String clientName)
   {
      this.clientName = clientName;
   }

   public String getClientNumber()
   {
      return this.clientNumber;
   }

   public void setClientNumber(final String clientNumber)
   {
      this.clientNumber = clientNumber;
   }

   public boolean getCanceled()
   {
      return this.canceled;
   }

   public void setCanceled(final boolean canceled)
   {
      this.canceled = canceled;
   }

   public String getRecordingUser()
   {
      return this.recordingUser;
   }

   public void setRecordingUser(final String recordingUser)
   {
      this.recordingUser = recordingUser;
   }

   public Date getModifiedDate()
   {
      return this.modifiedDate;
   }

   public void setModifiedDate(final Date modifiedDate)
   {
      this.modifiedDate = modifiedDate;
   }

   public VoucherType getVoucherType()
   {
      return this.voucherType;
   }

   public void setVoucherType(final VoucherType voucherType)
   {
      this.voucherType = voucherType;
   }

   public BigDecimal getAmountUsed()
   {
      return this.amountUsed;
   }

   public void setAmountUsed(final BigDecimal amountUsed)
   {
      this.amountUsed = amountUsed;
   }

   public boolean getSettled()
   {
      return this.settled;
   }

   public void setSettled(final boolean settled)
   {
      this.settled = settled;
   }

   public BigDecimal getRestAmount()
   {
      return this.restAmount;
   }

   public void setRestAmount(final BigDecimal restAmount)
   {
      this.restAmount = restAmount;
   }

   public boolean getVoucherPrinted()
   {
      return this.voucherPrinted;
   }

   public void setVoucherPrinted(final boolean voucherPrinted)
   {
      this.voucherPrinted = voucherPrinted;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (voucherNumber != null && !voucherNumber.trim().isEmpty())
         result += "voucherNumber: " + voucherNumber;
      if (clientName != null && !clientName.trim().isEmpty())
         result += ", clientName: " + clientName;
      if (clientNumber != null && !clientNumber.trim().isEmpty())
         result += ", clientNumber: " + clientNumber;
      result += ", canceled: " + canceled;
      if (recordingUser != null && !recordingUser.trim().isEmpty())
         result += ", recordingUser: " + recordingUser;
      result += ", settled: " + settled;
      result += ", voucherPrinted: " + voucherPrinted;
      return result;
   }
}