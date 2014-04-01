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
import org.adorsys.adpharma.server.jpa.Supplier;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import javax.validation.constraints.NotNull;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Delivery;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.SupplierInvoiceItem;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Description("SupplierInvoice_description")
@ListField({ "invoiceType", "invoiceNumber", "creationDate", "supplier.name",
      "creatingUser.fullName", "agency.name", "delivery.deliveryNumber",
      "settled", "amountBeforeTax", "taxAmount", "amountDiscount",
      "amountAfterTax", "netToPay", "advancePayment", "totalRestToPay" })
@ToStringField("invoiceNumber")
public class SupplierInvoice implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("SupplierInvoice_invoiceType_description")
   @Enumerated
   private InvoiceType invoiceType;

   @Column
   @Description("SupplierInvoice_invoiceNumber_description")
   private String invoiceNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("SupplierInvoice_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date creationDate;

   @ManyToOne
   @Description("SupplierInvoice_supplier_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Supplier.class)
   @NotNull(message = "SupplierInvoice_supplier_NotNull_validation")
   private Supplier supplier;

   @ManyToOne
   @Description("SupplierInvoice_creatingUser_description")
   @NotNull(message = "SupplierInvoice_creatingUser_NotNull_validation")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private Login creatingUser;

   @ManyToOne
   @Description("SupplierInvoice_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   @NotNull(message = "SupplierInvoice_agency_NotNull_validation")
   private Agency agency;

   @ManyToOne
   @Description("SupplierInvoice_delivery_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Delivery.class)
   private Delivery delivery;

   @Column
   @Description("SupplierInvoice_settled_description")
   private Boolean settled;

   @Column
   @Description("SupplierInvoice_amountBeforeTax_description")
   private BigDecimal amountBeforeTax;

   @Column
   @Description("SupplierInvoice_taxAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal taxAmount;

   @Column
   @Description("SupplierInvoice_amountDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountDiscount;

   @Column
   @Description("SupplierInvoice_amountAfterTax_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountAfterTax;

   @Column
   @Description("SupplierInvoice_netToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal netToPay;

   @Column
   @Description("SupplierInvoice_advancePayment_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal advancePayment;

   @Column
   @Description("SupplierInvoice_totalRestToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalRestToPay;

   @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
   @Description("SupplierInvoice_invoiceItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = SupplierInvoiceItem.class, selectionMode = SelectionMode.TABLE)
   private Set<SupplierInvoiceItem> invoiceItems = new HashSet<SupplierInvoiceItem>();

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
         return id.equals(((SupplierInvoice) that).id);
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

   public Supplier getSupplier()
   {
      return this.supplier;
   }

   public void setSupplier(final Supplier supplier)
   {
      this.supplier = supplier;
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

   public Delivery getDelivery()
   {
      return this.delivery;
   }

   public void setDelivery(final Delivery delivery)
   {
      this.delivery = delivery;
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

   public BigDecimal getTaxAmount()
   {
      return this.taxAmount;
   }

   public void setTaxAmount(final BigDecimal taxAmount)
   {
      this.taxAmount = taxAmount;
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
      return result;
   }

   public Set<SupplierInvoiceItem> getInvoiceItems()
   {
      return this.invoiceItems;
   }

   public void setInvoiceItems(final Set<SupplierInvoiceItem> invoiceItems)
   {
      this.invoiceItems = invoiceItems;
   }
}