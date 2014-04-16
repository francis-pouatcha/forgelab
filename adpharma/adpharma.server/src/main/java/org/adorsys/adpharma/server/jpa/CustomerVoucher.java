package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;

import javax.persistence.ManyToOne;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;

import java.math.BigDecimal;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.utils.SequenceGenerator;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.format.DateFormatPattern;
import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Description("CustomerVoucher_description")
@ToStringField("voucherNumber")
@ListField({ "voucherNumber", "salesOrder.soNumber", "amount", "customer.fullName",
      "agency.name", "canceled", "recordingUser", "modifiedDate",
      "amountUsed", "settled", "restAmount", "voucherPrinted" })
public class CustomerVoucher implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("CustomerVoucher_voucherNumber_description")
   private String voucherNumber;

   @ManyToOne
   @Description("CustomerVoucher_customerInvoice_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = CustomerInvoice.class)
   private CustomerInvoice customerInvoice;

   @Column
   @Description("CustomerVoucher_amount_description")
   @NumberFormatType(NumberType.CURRENCY)
   @NotNull(message = "CustomerVoucher_amount_NotNull_validation")
   private BigDecimal amount;

   @ManyToOne
   @Description("CustomerVoucher_customer_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private Customer customer;

   @ManyToOne
   @Description("CustomerVoucher_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private Agency agency;

   @Column
   @Description("CustomerVoucher_canceled_description")
   private Boolean canceled =Boolean.FALSE ;

   @ManyToOne
   @Description("CustomerVoucher_recordingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private Login recordingUser;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("CustomerVoucher_modifiedDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date modifiedDate;

   @Column
   @Description("CustomerVoucher_amountUsed_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amountUsed  = BigDecimal.ZERO;

   @Column
   @Description("CustomerVoucher_settled_description")
   private Boolean settled =Boolean.FALSE;

   @Column
   @Description("CustomerVoucher_restAmount_description")
   private BigDecimal restAmount = BigDecimal.ZERO;

   @Column
   @Description("CustomerVoucher_voucherPrinted_description")
   private Boolean voucherPrinted = Boolean.FALSE;
   
   @PrePersist
	public void prePersist(){
		modifiedDate = new Date();
		voucherNumber = SequenceGenerator.CUSTOMER_VOUCHER_SEQUENCE_PREFIXE +RandomStringUtils.randomNumeric(6);
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
         return id.equals(((CustomerVoucher) that).id);
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

   public CustomerInvoice getCustomerInvoice()
   {
      return this.customerInvoice;
   }

   public void setCustomerInvoice(final CustomerInvoice customerInvoice)
   {
      this.customerInvoice = customerInvoice;
   }

   public BigDecimal getAmount()
   {
      return this.amount;
   }

   public void setAmount(final BigDecimal amount)
   {
      this.amount = amount;
   }

   public Customer getCustomer()
   {
      return this.customer;
   }

   public void setCustomer(final Customer customer)
   {
      this.customer = customer;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
   }

   public Boolean getCanceled()
   {
      return this.canceled;
   }

   public void setCanceled(final Boolean canceled)
   {
      this.canceled = canceled;
   }

   public Login getRecordingUser()
   {
      return this.recordingUser;
   }

   public void setRecordingUser(final Login recordingUser)
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

   public BigDecimal getAmountUsed()
   {
      return this.amountUsed;
   }

   public void setAmountUsed(final BigDecimal amountUsed)
   {
      this.amountUsed = amountUsed;
   }

   public Boolean getSettled()
   {
      return this.settled;
   }

   public void setSettled(final Boolean settled)
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

   public Boolean getVoucherPrinted()
   {
      return this.voucherPrinted;
   }

   public void setVoucherPrinted(final Boolean voucherPrinted)
   {
      this.voucherPrinted = voucherPrinted;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (voucherNumber != null && !voucherNumber.trim().isEmpty())
         result += "voucherNumber: " + voucherNumber;
      if (canceled != null)
         result += ", canceled: " + canceled;
      if (settled != null)
         result += ", settled: " + settled;
      if (voucherPrinted != null)
         result += ", voucherPrinted: " + voucherPrinted;
      return result;
   }
}