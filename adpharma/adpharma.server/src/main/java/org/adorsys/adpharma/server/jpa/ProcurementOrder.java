package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

import javax.validation.constraints.NotNull;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.adpharma.server.jpa.Login;

import javax.persistence.ManyToOne;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.adpharma.server.jpa.ProcmtOrderTriggerMode;

import javax.persistence.Enumerated;

import org.adorsys.adpharma.server.jpa.ProcurementOrderType;
import org.adorsys.adpharma.server.jpa.Supplier;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.Agency;

import java.math.BigDecimal;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Description("ProcurementOrder_description")
@ToStringField("procurementOrderNumber")
@ListField({ "procurementOrderNumber", "poStatus", "agency.name", "amountBeforeTax",
      "amountAfterTax", "amountDiscount", "taxAmount", "netAmountToPay",
      "vat.rate" })
public class ProcurementOrder implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("ProcurementOrder_procurementOrderNumber_description")
   @NotNull(message = "ProcurementOrder_procurementOrderNumber_NotNull_validation")
   private String procurementOrderNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("ProcurementOrder_submissionDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date submissionDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("ProcurementOrder_createdDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date createdDate;

   @ManyToOne
   @Description("ProcurementOrder_creatingUser_description")
   @NotNull(message = "ProcurementOrder_creatingUser_NotNull_validation")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private Login creatingUser;

   @Column
   @Description("ProcurementOrder_procmtOrderTriggerMode_description")
   @Enumerated
   private ProcmtOrderTriggerMode procmtOrderTriggerMode;

   @Column
   @Description("ProcurementOrder_procurementOrderType_description")
   @Enumerated
   private ProcurementOrderType procurementOrderType;

   @ManyToOne
   @Description("ProcurementOrder_supplier_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Supplier.class)
   @NotNull(message = "ProcurementOrder_supplier_NotNull_validation")
   private Supplier supplier;

   @Column
   @Description("ProcurementOrder_poStatus_description")
   @Enumerated(EnumType.STRING)
   private DocumentProcessingState poStatus = DocumentProcessingState.ONGOING;

   @ManyToOne
   @Description("ProcurementOrder_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   @NotNull(message = "ProcurementOrder_agency_NotNull_validation")
   private Agency agency;

   @Column
   @Description("ProcurementOrder_amountBeforeTax_description")
   @NotNull(message = "ProcurementOrder_amountBeforeTax_NotNull_validation")
   private BigDecimal amountBeforeTax = BigDecimal.ZERO;

   @Column
   @Description("ProcurementOrder_amountAfterTax_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountAfterTax  = BigDecimal.ZERO;

   @Column
   @Description("ProcurementOrder_amountDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountDiscount = BigDecimal.ZERO;

   @Column
   @Description("ProcurementOrder_taxAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal taxAmount = BigDecimal.ZERO;

   @Column
   @Description("ProcurementOrder_netAmountToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal netAmountToPay = BigDecimal.ZERO;

   @ManyToOne
   @Description("ProcurementOrder_vat_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = VAT.class)
   private VAT vat;

   @OneToMany(mappedBy = "procurementOrder", cascade = CascadeType.ALL)
   @Description("ProcurementOrder_procurementOrderItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = ProcurementOrderItem.class, selectionMode = SelectionMode.TABLE)
   private Set<ProcurementOrderItem> procurementOrderItems = new HashSet<ProcurementOrderItem>();
   
   public void calculateAmount(){
	   BigDecimal vateRate = BigDecimal.ZERO;
	   if(vat !=null && vat.getRate()!=null){
		   BigDecimal ONDRED = BigDecimal.valueOf(100);
		   vateRate = vat.getRate().divide(ONDRED);
	   }
	   taxAmount = amountBeforeTax.multiply(vateRate);
	   amountAfterTax = amountBeforeTax.add(taxAmount);
	   netAmountToPay = amountBeforeTax.subtract(amountDiscount);
   }

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
         return id.equals(((ProcurementOrder) that).id);
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

   public String getProcurementOrderNumber()
   {
      return this.procurementOrderNumber;
   }

   public void setProcurementOrderNumber(final String procurementOrderNumber)
   {
      this.procurementOrderNumber = procurementOrderNumber;
   }

   public Date getSubmissionDate()
   {
      return this.submissionDate;
   }

   public void setSubmissionDate(final Date submissionDate)
   {
      this.submissionDate = submissionDate;
   }

   public Date getCreatedDate()
   {
      return this.createdDate;
   }

   public void setCreatedDate(final Date createdDate)
   {
      this.createdDate = createdDate;
   }

   public Login getCreatingUser()
   {
      return this.creatingUser;
   }

   public void setCreatingUser(final Login creatingUser)
   {
      this.creatingUser = creatingUser;
   }

   public ProcmtOrderTriggerMode getProcmtOrderTriggerMode()
   {
      return this.procmtOrderTriggerMode;
   }

   public void setProcmtOrderTriggerMode(
         final ProcmtOrderTriggerMode procmtOrderTriggerMode)
   {
      this.procmtOrderTriggerMode = procmtOrderTriggerMode;
   }

   public ProcurementOrderType getProcurementOrderType()
   {
      return this.procurementOrderType;
   }

   public void setProcurementOrderType(
         final ProcurementOrderType procurementOrderType)
   {
      this.procurementOrderType = procurementOrderType;
   }

   public Supplier getSupplier()
   {
      return this.supplier;
   }

   public void setSupplier(final Supplier supplier)
   {
      this.supplier = supplier;
   }

   public DocumentProcessingState getPoStatus()
   {
      return this.poStatus;
   }

   public void setPoStatus(final DocumentProcessingState poStatus)
   {
      this.poStatus = poStatus;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
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

   public BigDecimal getTaxAmount()
   {
      return this.taxAmount;
   }

   public void setTaxAmount(final BigDecimal taxAmount)
   {
      this.taxAmount = taxAmount;
   }

   public BigDecimal getNetAmountToPay()
   {
      return this.netAmountToPay;
   }

   public void setNetAmountToPay(final BigDecimal netAmountToPay)
   {
      this.netAmountToPay = netAmountToPay;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (procurementOrderNumber != null
            && !procurementOrderNumber.trim().isEmpty())
         result += "procurementOrderNumber: " + procurementOrderNumber;
      return result;
   }

   public VAT getVat()
   {
      return this.vat;
   }

   public void setVat(final VAT vat)
   {
      this.vat = vat;
   }

   public Set<ProcurementOrderItem> getProcurementOrderItems()
   {
      return this.procurementOrderItems;
   }

   public void setProcurementOrderItems(
         final Set<ProcurementOrderItem> procurementOrderItems)
   {
      this.procurementOrderItems = procurementOrderItems;
   }
}