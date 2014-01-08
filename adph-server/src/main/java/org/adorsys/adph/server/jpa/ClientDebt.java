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
import org.adorsys.adph.server.jpa.Invoice;
import javax.persistence.ManyToOne;
import org.adorsys.adph.server.jpa.Client;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.DebtStatement;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.ClientDebt.description")
@Audited
@XmlRootElement
public class ClientDebt implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.ClientDebt.invoice.description")
   private Invoice invoice;

   @Column
   private String clientNumber;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.ClientDebt.client.description")
   private Client client;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientDebt.clientName.description")
   private String clientName;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ClientDebt.creationDate.description")
   private Date creationDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ClientDebt.endDate.description")
   private Date endDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientDebt.initialAmount.description")
   private BigDecimal initialAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientDebt.advancePayment.description")
   private BigDecimal advancePayment;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientDebt.restAmount.description")
   private BigDecimal restAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientDebt.settled.description")
   private boolean settled;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ClientDebt.lastPaymentDate.description")
   private Date lastPaymentDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClientDebt.canceled.description")
   private boolean canceled;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ClientDebt.paymentDate.description")
   private Date paymentDate;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.ClientDebt.debtStatement.description")
   private DebtStatement debtStatement;

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
         return id.equals(((ClientDebt) that).id);
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

   public Invoice getInvoice()
   {
      return this.invoice;
   }

   public void setInvoice(final Invoice invoice)
   {
      this.invoice = invoice;
   }

   public String getClientNumber()
   {
      return this.clientNumber;
   }

   public void setClientNumber(final String clientNumber)
   {
      this.clientNumber = clientNumber;
   }

   public Client getClient()
   {
      return this.client;
   }

   public void setClient(final Client client)
   {
      this.client = client;
   }

   public String getClientName()
   {
      return this.clientName;
   }

   public void setClientName(final String clientName)
   {
      this.clientName = clientName;
   }

   public Date getCreationDate()
   {
      return this.creationDate;
   }

   public void setCreationDate(final Date creationDate)
   {
      this.creationDate = creationDate;
   }

   public Date getEndDate()
   {
      return this.endDate;
   }

   public void setEndDate(final Date endDate)
   {
      this.endDate = endDate;
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

   public Date getLastPaymentDate()
   {
      return this.lastPaymentDate;
   }

   public void setLastPaymentDate(final Date lastPaymentDate)
   {
      this.lastPaymentDate = lastPaymentDate;
   }

   public boolean getCanceled()
   {
      return this.canceled;
   }

   public void setCanceled(final boolean canceled)
   {
      this.canceled = canceled;
   }

   public Date getPaymentDate()
   {
      return this.paymentDate;
   }

   public void setPaymentDate(final Date paymentDate)
   {
      this.paymentDate = paymentDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (clientNumber != null && !clientNumber.trim().isEmpty())
         result += "clientNumber: " + clientNumber;
      if (clientName != null && !clientName.trim().isEmpty())
         result += ", clientName: " + clientName;
      result += ", settled: " + settled;
      result += ", canceled: " + canceled;
      return result;
   }

   public DebtStatement getDebtStatement()
   {
      return this.debtStatement;
   }

   public void setDebtStatement(final DebtStatement debtStatement)
   {
      this.debtStatement = debtStatement;
   }
}