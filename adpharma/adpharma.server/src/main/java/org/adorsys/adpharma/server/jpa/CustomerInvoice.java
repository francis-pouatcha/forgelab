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
import org.adorsys.adpharma.server.jpa.InvoiceType;
import javax.persistence.Enumerated;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.adpharma.server.jpa.Customer;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import javax.validation.constraints.NotNull;
import org.adorsys.adpharma.server.jpa.Insurrance;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;

@Entity
@Description("CustomerInvoice_description")
@ListField({ "invoiceType", "invoiceNumber", "creationDate", "customer.fullName",
      "insurance.customer.fullName", "insurance.insurer.fullName",
      "creatingUser.fullName", "agency.name", "salesOrder.soNumber",
      "settled", "amountBeforeTax", "amountVAT", "amountDiscount",
      "amountAfterTax", "netToPay", "customerRestTopay",
      "insurranceRestTopay", "cashed", "advancePayment", "totalRestToPay" })
@ToStringField("invoiceNumber")
public class CustomerInvoice implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("CustomerInvoice_invoiceType_description")
   @Enumerated
   private InvoiceType invoiceType;

   @Column
   @Description("CustomerInvoice_invoiceNumber_description")
   private String invoiceNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("CustomerInvoice_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date creationDate;

   @ManyToOne
   @Description("CustomerInvoice_customer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   @NotNull(message = "CustomerInvoice_customer_NotNull_validation")
   private Customer customer;

   @ManyToOne
   @Description("CustomerInvoice_insurance_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Insurrance.class)
   private Insurrance insurance;

   @ManyToOne
   @Description("CustomerInvoice_creatingUser_description")
   @NotNull(message = "CustomerInvoice_creatingUser_NotNull_validation")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private Login creatingUser;

   @ManyToOne
   @Description("CustomerInvoice_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   @NotNull(message = "CustomerInvoice_agency_NotNull_validation")
   private Agency agency;

   @ManyToOne
   @Description("CustomerInvoice_salesOrder_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = SalesOrder.class)
   private SalesOrder salesOrder;

   @Column
   @Description("CustomerInvoice_settled_description")
   private Boolean settled;

   @Column
   @Description("CustomerInvoice_amountBeforeTax_description")
   private BigDecimal amountBeforeTax;

   @Column
   @Description("CustomerInvoice_amountVAT_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountVAT;

   @Column
   @Description("CustomerInvoice_amountDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountDiscount;

   @Column
   @Description("CustomerInvoice_amountAfterTax_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountAfterTax;

   @Column
   @Description("CustomerInvoice_netToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal netToPay;

   @Column
   @Description("CustomerInvoice_customerRestTopay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal customerRestTopay;

   @Column
   @Description("CustomerInvoice_insurranceRestTopay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal insurranceRestTopay;

   @Column
   @Description("CustomerInvoice_cashed_description")
   private Boolean cashed;

   @Column
   @Description("CustomerInvoice_advancePayment_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal advancePayment;

   @Column
   @Description("CustomerInvoice_totalRestToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalRestToPay;

   @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
   @Description("CustomerInvoice_invoiceItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = CustomerInvoiceItem.class, selectionMode = SelectionMode.TABLE)
   private Set<CustomerInvoiceItem> invoiceItems = new HashSet<CustomerInvoiceItem>();

   @OneToMany(mappedBy = "target", targetEntity = PaymentCustomerInvoiceAssoc.class)
   @Relationship(end = RelationshipEnd.TARGET, sourceEntity = Payment.class, targetEntity = CustomerInvoice.class, sourceQualifier = "invoices", targetQualifier = "payments")
   @Description("CustomerInvoice_payments_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = Payment.class)
   private Set<PaymentCustomerInvoiceAssoc> payments = new HashSet<PaymentCustomerInvoiceAssoc>();

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
         return id.equals(((CustomerInvoice) that).id);
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

   public InvoiceType getInvoiceType()
   {
      return this.invoiceType;
   }

   public void setInvoiceType(final InvoiceType invoiceType)
   {
      this.invoiceType = invoiceType;
   }

   public String getInvoiceNumber()
   {
      return this.invoiceNumber;
   }

   public void setInvoiceNumber(final String invoiceNumber)
   {
      this.invoiceNumber = invoiceNumber;
   }

   public Date getCreationDate()
   {
      return this.creationDate;
   }

   public void setCreationDate(final Date creationDate)
   {
      this.creationDate = creationDate;
   }

   public Customer getCustomer()
   {
      return this.customer;
   }

   public void setCustomer(final Customer customer)
   {
      this.customer = customer;
   }

   public Insurrance getInsurance()
   {
      return this.insurance;
   }

   public void setInsurance(final Insurrance insurance)
   {
      this.insurance = insurance;
   }

   public Login getCreatingUser()
   {
      return this.creatingUser;
   }

   public void setCreatingUser(final Login creatingUser)
   {
      this.creatingUser = creatingUser;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
   }

   public SalesOrder getSalesOrder()
   {
      return this.salesOrder;
   }

   public void setSalesOrder(final SalesOrder salesOrder)
   {
      this.salesOrder = salesOrder;
   }

   public Boolean getSettled()
   {
      return this.settled;
   }

   public void setSettled(final Boolean settled)
   {
      this.settled = settled;
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

   public BigDecimal getNetToPay()
   {
      return this.netToPay;
   }

   public void setNetToPay(final BigDecimal netToPay)
   {
      this.netToPay = netToPay;
   }

   public BigDecimal getCustomerRestTopay()
   {
      return this.customerRestTopay;
   }

   public void setCustomerRestTopay(final BigDecimal customerRestTopay)
   {
      this.customerRestTopay = customerRestTopay;
   }

   public BigDecimal getInsurranceRestTopay()
   {
      return this.insurranceRestTopay;
   }

   public void setInsurranceRestTopay(final BigDecimal insurranceRestTopay)
   {
      this.insurranceRestTopay = insurranceRestTopay;
   }

   public Boolean getCashed()
   {
      return this.cashed;
   }

   public void setCashed(final Boolean cashed)
   {
      this.cashed = cashed;
   }

   public BigDecimal getAdvancePayment()
   {
      return this.advancePayment;
   }

   public void setAdvancePayment(final BigDecimal advancePayment)
   {
      this.advancePayment = advancePayment;
   }

   public BigDecimal getTotalRestToPay()
   {
      return this.totalRestToPay;
   }

   public void setTotalRestToPay(final BigDecimal totalRestToPay)
   {
      this.totalRestToPay = totalRestToPay;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (invoiceNumber != null && !invoiceNumber.trim().isEmpty())
         result += "invoiceNumber: " + invoiceNumber;
      if (settled != null)
         result += ", settled: " + settled;
      if (cashed != null)
         result += ", cashed: " + cashed;
      return result;
   }

   public Set<CustomerInvoiceItem> getInvoiceItems()
   {
      return this.invoiceItems;
   }

   public void setInvoiceItems(final Set<CustomerInvoiceItem> invoiceItems)
   {
      this.invoiceItems = invoiceItems;
   }

   public Set<PaymentCustomerInvoiceAssoc> getPayments()
   {
      return this.payments;
   }

   public void setPayments(final Set<PaymentCustomerInvoiceAssoc> payments)
   {
      this.payments = payments;
   }
}