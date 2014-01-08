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
import org.adorsys.adph.server.jpa.Client;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.DebtStatement.description")
@Audited
@XmlRootElement
public class DebtStatement implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.DebtStatement.statementNumber.description")
   private String statementNumber;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.DebtStatement.client.description")
   private Client client;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.DebtStatement.modifDate.description")
   private Date modifDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.DebtStatement.paymentDate.description")
   private Date paymentDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.DebtStatement.initialAmount.description")
   private BigDecimal initialAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.DebtStatement.advancePayment.description")
   private BigDecimal advancePayment;

   @Column
   @Description("org.adorsys.adph.server.jpa.DebtStatement.restAmount.description")
   private BigDecimal restAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.DebtStatement.settled.description")
   private boolean settled;

   @Column
   @Description("org.adorsys.adph.server.jpa.DebtStatement.amountFromVouchers.description")
   private BigDecimal amountFromVouchers;

   @Column
   @Description("org.adorsys.adph.server.jpa.DebtStatement.canceled.description")
   private boolean canceled;

   @Column
   @Description("org.adorsys.adph.server.jpa.DebtStatement.useVoucher.description")
   private boolean useVoucher;

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
         return id.equals(((DebtStatement) that).id);
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

   public String getStatementNumber()
   {
      return this.statementNumber;
   }

   public void setStatementNumber(final String statementNumber)
   {
      this.statementNumber = statementNumber;
   }

   public Client getClient()
   {
      return this.client;
   }

   public void setClient(final Client client)
   {
      this.client = client;
   }

   public Date getModifDate()
   {
      return this.modifDate;
   }

   public void setModifDate(final Date modifDate)
   {
      this.modifDate = modifDate;
   }

   public Date getPaymentDate()
   {
      return this.paymentDate;
   }

   public void setPaymentDate(final Date paymentDate)
   {
      this.paymentDate = paymentDate;
   }

   public BigDecimal getInitialAmount()
   {
      return this.initialAmount;
   }

   public void setInitialAmount(final BigDecimal initialAmount)
   {
      this.initialAmount = initialAmount;
   }

   public BigDecimal getAdvancePayment()
   {
      return this.advancePayment;
   }

   public void setAdvancePayment(final BigDecimal advancePayment)
   {
      this.advancePayment = advancePayment;
   }

   public BigDecimal getRestAmount()
   {
      return this.restAmount;
   }

   public void setRestAmount(final BigDecimal restAmount)
   {
      this.restAmount = restAmount;
   }

   public boolean getSettled()
   {
      return this.settled;
   }

   public void setSettled(final boolean settled)
   {
      this.settled = settled;
   }

   public BigDecimal getAmountFromVouchers()
   {
      return this.amountFromVouchers;
   }

   public void setAmountFromVouchers(final BigDecimal amountFromVouchers)
   {
      this.amountFromVouchers = amountFromVouchers;
   }

   public boolean getCanceled()
   {
      return this.canceled;
   }

   public void setCanceled(final boolean canceled)
   {
      this.canceled = canceled;
   }

   public boolean getUseVoucher()
   {
      return this.useVoucher;
   }

   public void setUseVoucher(final boolean useVoucher)
   {
      this.useVoucher = useVoucher;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (statementNumber != null && !statementNumber.trim().isEmpty())
         result += "statementNumber: " + statementNumber;
      result += ", settled: " + settled;
      result += ", canceled: " + canceled;
      result += ", useVoucher: " + useVoucher;
      return result;
   }
}