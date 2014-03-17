package org.adorsys.adpharma.client.jpa.customervoucher;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("CustomerVoucher_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("voucherNumber")
@ListField({ "voucherNumber", "salesOrder.soNumber", "amount",
      "customer.fullName", "agency.name", "canceled", "recordingUser",
      "modifiedDate", "amountUsed", "settled", "restAmount", "voucherPrinted" })
public class CustomerVoucher
{

   private Long id;
   private int version;

   @Description("CustomerVoucher_voucherNumber_description")
   private SimpleStringProperty voucherNumber;
   @Description("CustomerVoucher_canceled_description")
   private SimpleBooleanProperty canceled;
   @Description("CustomerVoucher_settled_description")
   private SimpleBooleanProperty settled;
   @Description("CustomerVoucher_voucherPrinted_description")
   private SimpleBooleanProperty voucherPrinted;
   @Description("CustomerVoucher_amount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amount;
   @Description("CustomerVoucher_amountUsed_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountUsed;
   @Description("CustomerVoucher_restAmount_description")
   private SimpleObjectProperty<BigDecimal> restAmount;
   @Description("CustomerVoucher_modifiedDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> modifiedDate;
   @Description("CustomerVoucher_customerInvoice_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = CustomerInvoice.class)
   private SimpleObjectProperty<CustomerVoucherCustomerInvoice> customerInvoice;
   @Description("CustomerVoucher_customer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private SimpleObjectProperty<CustomerVoucherCustomer> customer;
   @Description("CustomerVoucher_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<CustomerVoucherAgency> agency;
   @Description("CustomerVoucher_recordingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<CustomerVoucherRecordingUser> recordingUser;

   public Long getId()
   {
      return id;
   }

   public final void setId(Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return version;
   }

   public final void setVersion(int version)
   {
      this.version = version;
   }

   public SimpleStringProperty voucherNumberProperty()
   {
      if (voucherNumber == null)
      {
         voucherNumber = new SimpleStringProperty();
      }
      return voucherNumber;
   }

   public String getVoucherNumber()
   {
      return voucherNumberProperty().get();
   }

   public final void setVoucherNumber(String voucherNumber)
   {
      this.voucherNumberProperty().set(voucherNumber);
   }

   public SimpleBooleanProperty canceledProperty()
   {
      if (canceled == null)
      {
         canceled = new SimpleBooleanProperty();
      }
      return canceled;
   }

   public Boolean getCanceled()
   {
      return canceledProperty().get();
   }

   public final void setCanceled(Boolean canceled)
   {
      if (canceled == null)
         canceled = Boolean.FALSE;
      this.canceledProperty().set(canceled);
   }

   public SimpleBooleanProperty settledProperty()
   {
      if (settled == null)
      {
         settled = new SimpleBooleanProperty();
      }
      return settled;
   }

   public Boolean getSettled()
   {
      return settledProperty().get();
   }

   public final void setSettled(Boolean settled)
   {
      if (settled == null)
         settled = Boolean.FALSE;
      this.settledProperty().set(settled);
   }

   public SimpleBooleanProperty voucherPrintedProperty()
   {
      if (voucherPrinted == null)
      {
         voucherPrinted = new SimpleBooleanProperty();
      }
      return voucherPrinted;
   }

   public Boolean getVoucherPrinted()
   {
      return voucherPrintedProperty().get();
   }

   public final void setVoucherPrinted(Boolean voucherPrinted)
   {
      if (voucherPrinted == null)
         voucherPrinted = Boolean.FALSE;
      this.voucherPrintedProperty().set(voucherPrinted);
   }

   public SimpleObjectProperty<BigDecimal> amountProperty()
   {
      if (amount == null)
      {
         amount = new SimpleObjectProperty<BigDecimal>();
      }
      return amount;
   }

   @NotNull(message = "CustomerVoucher_amount_NotNull_validation")
   public BigDecimal getAmount()
   {
      return amountProperty().get();
   }

   public final void setAmount(BigDecimal amount)
   {
      this.amountProperty().set(amount);
   }

   public SimpleObjectProperty<BigDecimal> amountUsedProperty()
   {
      if (amountUsed == null)
      {
         amountUsed = new SimpleObjectProperty<BigDecimal>();
      }
      return amountUsed;
   }

   public BigDecimal getAmountUsed()
   {
      return amountUsedProperty().get();
   }

   public final void setAmountUsed(BigDecimal amountUsed)
   {
      this.amountUsedProperty().set(amountUsed);
   }

   public SimpleObjectProperty<BigDecimal> restAmountProperty()
   {
      if (restAmount == null)
      {
         restAmount = new SimpleObjectProperty<BigDecimal>();
      }
      return restAmount;
   }

   public BigDecimal getRestAmount()
   {
      return restAmountProperty().get();
   }

   public final void setRestAmount(BigDecimal restAmount)
   {
      this.restAmountProperty().set(restAmount);
   }

   public SimpleObjectProperty<Calendar> modifiedDateProperty()
   {
      if (modifiedDate == null)
      {
         modifiedDate = new SimpleObjectProperty<Calendar>();
      }
      return modifiedDate;
   }

   public Calendar getModifiedDate()
   {
      return modifiedDateProperty().get();
   }

   public final void setModifiedDate(Calendar modifiedDate)
   {
      this.modifiedDateProperty().set(modifiedDate);
   }

   public SimpleObjectProperty<CustomerVoucherCustomerInvoice> customerInvoiceProperty()
   {
      if (customerInvoice == null)
      {
         customerInvoice = new SimpleObjectProperty<CustomerVoucherCustomerInvoice>(new CustomerVoucherCustomerInvoice());
      }
      return customerInvoice;
   }

   public CustomerVoucherCustomerInvoice getCustomerInvoice()
   {
      return customerInvoiceProperty().get();
   }

   public final void setCustomerInvoice(CustomerVoucherCustomerInvoice customerInvoice)
   {
      if (customerInvoice == null)
      {
         customerInvoice = new CustomerVoucherCustomerInvoice();
      }
      PropertyReader.copy(customerInvoice, getCustomerInvoice());
   }

   public SimpleObjectProperty<CustomerVoucherCustomer> customerProperty()
   {
      if (customer == null)
      {
         customer = new SimpleObjectProperty<CustomerVoucherCustomer>(new CustomerVoucherCustomer());
      }
      return customer;
   }

   public CustomerVoucherCustomer getCustomer()
   {
      return customerProperty().get();
   }

   public final void setCustomer(CustomerVoucherCustomer customer)
   {
      if (customer == null)
      {
         customer = new CustomerVoucherCustomer();
      }
      PropertyReader.copy(customer, getCustomer());
   }

   public SimpleObjectProperty<CustomerVoucherAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<CustomerVoucherAgency>(new CustomerVoucherAgency());
      }
      return agency;
   }

   public CustomerVoucherAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(CustomerVoucherAgency agency)
   {
      if (agency == null)
      {
         agency = new CustomerVoucherAgency();
      }
      PropertyReader.copy(agency, getAgency());
   }

   public SimpleObjectProperty<CustomerVoucherRecordingUser> recordingUserProperty()
   {
      if (recordingUser == null)
      {
         recordingUser = new SimpleObjectProperty<CustomerVoucherRecordingUser>(new CustomerVoucherRecordingUser());
      }
      return recordingUser;
   }

   public CustomerVoucherRecordingUser getRecordingUser()
   {
      return recordingUserProperty().get();
   }

   public final void setRecordingUser(CustomerVoucherRecordingUser recordingUser)
   {
      if (recordingUser == null)
      {
         recordingUser = new CustomerVoucherRecordingUser();
      }
      PropertyReader.copy(recordingUser, getRecordingUser());
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      CustomerVoucher other = (CustomerVoucher) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "voucherNumber");
   }
}