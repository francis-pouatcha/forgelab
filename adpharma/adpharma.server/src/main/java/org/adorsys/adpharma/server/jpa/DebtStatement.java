package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.adpharma.server.jpa.Customer;

import javax.persistence.ManyToOne;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.adpharma.server.jpa.Agency;

import javax.validation.constraints.NotNull;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.format.DateFormatPattern;

import java.math.BigDecimal;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.OneToMany;

import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;

@Entity
@Description("DebtStatement_description")
@ToStringField("statementNumber")
@ListField({ "statementNumber", "insurrance.fullName", "agency.name", "paymentDate",
      "initialAmount", "advancePayment", "restAmount", "settled",
      "amountFromVouchers", "canceled", "useVoucher" })
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
   @Description("DebtStatement_statementNumber_description")
   private String statementNumber;

   @ManyToOne
   @Description("DebtStatement_insurrance_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private Customer insurrance;

   @ManyToOne
   @Description("DebtStatement_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   @NotNull(message = "DebtStatement_agency_NotNull_validation")
   private Agency agency;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("DebtStatement_paymentDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date paymentDate;

   @Column
   @Description("DebtStatement_initialAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal initialAmount;

   @Column
   @Description("DebtStatement_advancePayment_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal advancePayment;

   @Column
   @Description("DebtStatement_restAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal restAmount;

   @Column
   @Description("DebtStatement_settled_description")
   private Boolean settled;

   @Column
   @Description("DebtStatement_amountFromVouchers_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountFromVouchers;

   @Column
   @Description("DebtStatement_canceled_description")
   private Boolean canceled;

   @Column
   @Description("DebtStatement_useVoucher_description")
   private Boolean useVoucher;

   @OneToMany(mappedBy = "source", targetEntity = DebtStatementCustomerInvoiceAssoc.class)
   @Relationship(end = RelationshipEnd.SOURCE, sourceEntity = DebtStatement.class, targetEntity = CustomerInvoice.class, sourceQualifier = "invoices")
   @Description("DebtStatement_invoices_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = CustomerInvoice.class)
   private Set<DebtStatementCustomerInvoiceAssoc> invoices = new HashSet<DebtStatementCustomerInvoiceAssoc>();

   @Column
   @Description("DebtStatement_statementStatus_description")
   @Enumerated
   private DocumentProcessingState statementStatus = DocumentProcessingState.ONGOING;
   
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

   public Customer getInsurrance()
   {
      return this.insurrance;
   }

   public void setInsurrance(final Customer insurrance)
   {
      this.insurrance = insurrance;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
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

   public Boolean getSettled()
   {
      return this.settled;
   }

   public void setSettled(final Boolean settled)
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

   public Boolean getCanceled()
   {
      return this.canceled;
   }

   public void setCanceled(final Boolean canceled)
   {
      this.canceled = canceled;
   }

   public Boolean getUseVoucher()
   {
      return this.useVoucher;
   }

   public void setUseVoucher(final Boolean useVoucher)
   {
      this.useVoucher = useVoucher;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (statementNumber != null && !statementNumber.trim().isEmpty())
         result += "statementNumber: " + statementNumber;
      if (settled != null)
         result += ", settled: " + settled;
      if (canceled != null)
         result += ", canceled: " + canceled;
      if (useVoucher != null)
         result += ", useVoucher: " + useVoucher;
      return result;
   }

   public Set<DebtStatementCustomerInvoiceAssoc> getInvoices()
   {
      return this.invoices;
   }

   public void setInvoices(final Set<DebtStatementCustomerInvoiceAssoc> invoices)
   {
      this.invoices = invoices;
   }
}