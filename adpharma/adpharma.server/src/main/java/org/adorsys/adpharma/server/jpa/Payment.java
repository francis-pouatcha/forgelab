package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.Agency;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import javax.validation.constraints.NotNull;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.CashDrawer;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;
import org.adorsys.adpharma.server.jpa.PaymentMode;
import javax.persistence.Enumerated;
import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.PaymentItem;
import javax.persistence.CascadeType;

@Entity
@Description("Payment_description")
@ToStringField("paymentNumber")
@ListField({ "paymentNumber", "paymentDate", "recordDate", "amount", "receivedAmount",
      "difference", "agency.name", "cashier.fullName",
      "cashDrawer.cashDrawerNumber", "paymentMode", "paymentReceiptPrinted",
      "paidBy.fullName" })
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
   @Description("Payment_paymentNumber_description")
   private String paymentNumber;
   
   @Column
   @Description("Payment_statementNumber_description")
   private String statementNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Payment_paymentDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date paymentDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Payment_recordDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date recordDate;

   @Column
   @Description("Payment_amount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amount;

   @Column
   @Description("Payment_receivedAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal receivedAmount;

   @Column
   @Description("Payment_difference_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal difference;

   @ManyToOne
   @Description("Payment_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   @NotNull(message = "Payment_agency_NotNull_validation")
   private Agency agency;

   @ManyToOne
   @Description("Payment_cashier_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   @NotNull(message = "Payment_cashier_NotNull_validation")
   private Login cashier;

   @ManyToOne
   @Description("Payment_cashDrawer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = CashDrawer.class)
   private CashDrawer cashDrawer;

   @OneToMany(mappedBy = "source", targetEntity = PaymentCustomerInvoiceAssoc.class, cascade=CascadeType.PERSIST)
   @Relationship(end = RelationshipEnd.SOURCE, sourceEntity = Payment.class, targetEntity = CustomerInvoice.class, sourceQualifier = "invoices", targetQualifier = "payments")
   @Description("Payment_invoices_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = CustomerInvoice.class, selectionMode = SelectionMode.TABLE)
   private Set<PaymentCustomerInvoiceAssoc> invoices = new HashSet<PaymentCustomerInvoiceAssoc>();

   @Column
   @Description("Payment_paymentMode_description")
   @Enumerated
   private PaymentMode paymentMode;

   @Column
   @Description("Payment_paymentReceiptPrinted_description")
   private Boolean paymentReceiptPrinted;

   @ManyToOne
   @Description("Payment_paidBy_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private Customer paidBy;

   @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
   @Description("Payment_paymentItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = PaymentItem.class, selectionMode = SelectionMode.TABLE)
   private Set<PaymentItem> paymentItems = new HashSet<PaymentItem>();

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

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
   }

   public Login getCashier()
   {
      return this.cashier;
   }

   public void setCashier(final Login cashier)
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

   public Set<PaymentCustomerInvoiceAssoc> getInvoices()
   {
      return this.invoices;
   }

   public void setInvoices(final Set<PaymentCustomerInvoiceAssoc> invoices)
   {
      this.invoices = invoices;
   }

   public PaymentMode getPaymentMode()
   {
      return this.paymentMode;
   }

   public void setPaymentMode(final PaymentMode paymentMode)
   {
      this.paymentMode = paymentMode;
   }

   public Boolean getPaymentReceiptPrinted()
   {
      return this.paymentReceiptPrinted;
   }

   public void setPaymentReceiptPrinted(final Boolean paymentReceiptPrinted)
   {
      this.paymentReceiptPrinted = paymentReceiptPrinted;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (paymentNumber != null && !paymentNumber.trim().isEmpty())
         result += "paymentNumber: " + paymentNumber;
      if (paymentReceiptPrinted != null)
         result += ", paymentReceiptPrinted: " + paymentReceiptPrinted;
      return result;
   }

   public Customer getPaidBy()
   {
      return this.paidBy;
   }

   public void setPaidBy(final Customer paidBy)
   {
      this.paidBy = paidBy;
   }

   public Set<PaymentItem> getPaymentItems()
   {
      return this.paymentItems;
   }

   public void setPaymentItems(final Set<PaymentItem> paymentItems)
   {
      this.paymentItems = paymentItems;
   }

public String getStatementNumber() {
	return statementNumber;
}

public void setStatementNumber(String statementNumber) {
	this.statementNumber = statementNumber;
}
   
   
}