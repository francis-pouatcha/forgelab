package org.adorsys.adpharma.client.jpa.payment;

import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;

import javafx.beans.property.SimpleBooleanProperty;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc.PaymentCustomerInvoiceAssoc;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;

import javax.validation.constraints.NotNull;

import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Payment_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("paymentNumber")
@ListField({ "paymentNumber", "paymentDate", "recordDate", "amount",
      "receivedAmount", "difference", "agency.name", "cashier.fullName",
      "cashDrawer.cashDrawerNumber", "paymentMode", "paymentReceiptPrinted",
      "paidBy.fullName" })
public class Payment implements Cloneable
{

   private Long id;
   private int version;

   @Description("Payment_paymentNumber_description")
   private SimpleStringProperty paymentNumber;
   @Description("Payment_paymentReceiptPrinted_description")
   private SimpleBooleanProperty paymentReceiptPrinted;
   @Description("Payment_paymentMode_description")
   private SimpleObjectProperty<PaymentMode> paymentMode;
   @Description("Payment_amount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amount;
   @Description("Payment_receivedAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> receivedAmount;
   @Description("Payment_difference_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> difference;
   @Description("Payment_paymentDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> paymentDate;
   @Description("Payment_recordDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordDate;
   @Description("Payment_paymentItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = PaymentItem.class, selectionMode = SelectionMode.TABLE)
   private SimpleObjectProperty<ObservableList<PaymentItem>> paymentItems;
   @Description("Payment_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<PaymentAgency> agency;
   @Description("Payment_cashier_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<PaymentCashier> cashier;
   @Description("Payment_cashDrawer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = CashDrawer.class)
   private SimpleObjectProperty<PaymentCashDrawer> cashDrawer;
   @Relationship(end = RelationshipEnd.SOURCE, sourceEntity = Payment.class, targetEntity = CustomerInvoice.class, sourceQualifier = "invoices", targetQualifier = "payments")
   @Description("Payment_invoices_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = CustomerInvoice.class, selectionMode = SelectionMode.TABLE)
   private SimpleObjectProperty<ObservableList<PaymentCustomerInvoiceAssoc>> invoices;
   @Description("Payment_paidBy_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private SimpleObjectProperty<PaymentPaidBy> paidBy;

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

   public SimpleStringProperty paymentNumberProperty()
   {
      if (paymentNumber == null)
      {
         paymentNumber = new SimpleStringProperty();
      }
      return paymentNumber;
   }

   public String getPaymentNumber()
   {
      return paymentNumberProperty().get();
   }

   public final void setPaymentNumber(String paymentNumber)
   {
      this.paymentNumberProperty().set(paymentNumber);
   }

   public SimpleBooleanProperty paymentReceiptPrintedProperty()
   {
      if (paymentReceiptPrinted == null)
      {
         paymentReceiptPrinted = new SimpleBooleanProperty(Boolean.FALSE);
      }
      return paymentReceiptPrinted;
   }

   public Boolean getPaymentReceiptPrinted()
   {
      return paymentReceiptPrintedProperty().get();
   }

   public final void setPaymentReceiptPrinted(Boolean paymentReceiptPrinted)
   {
      if (paymentReceiptPrinted == null)
         paymentReceiptPrinted = Boolean.FALSE;
      this.paymentReceiptPrintedProperty().set(paymentReceiptPrinted);
   }

   public SimpleObjectProperty<PaymentMode> paymentModeProperty()
   {
      if (paymentMode == null)
      {
         paymentMode = new SimpleObjectProperty<PaymentMode>(PaymentMode.CASH);
      }
      return paymentMode;
   }

   public PaymentMode getPaymentMode()
   {
      return paymentModeProperty().get();
   }

   public final void setPaymentMode(PaymentMode paymentMode)
   {
      this.paymentModeProperty().set(paymentMode);
   }

   public SimpleObjectProperty<BigDecimal> amountProperty()
   {
      if (amount == null)
      {
         amount = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
      }
      return amount;
   }

   public BigDecimal getAmount()
   {
      return amountProperty().get();
   }

   public final void setAmount(BigDecimal amount)
   {
      this.amountProperty().set(amount);
   }

   public SimpleObjectProperty<BigDecimal> receivedAmountProperty()
   {
      if (receivedAmount == null)
      {
         receivedAmount = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
      }
      return receivedAmount;
   }

   public BigDecimal getReceivedAmount()
   {
      return receivedAmountProperty().get();
   }

   public final void setReceivedAmount(BigDecimal receivedAmount)
   {
      this.receivedAmountProperty().set(receivedAmount);
   }

   public SimpleObjectProperty<BigDecimal> differenceProperty()
   {
      if (difference == null)
      {
         difference = new SimpleObjectProperty<BigDecimal>(BigDecimal.ZERO);
      }
      return difference;
   }

   public BigDecimal getDifference()
   {
      return differenceProperty().get();
   }

   public final void setDifference(BigDecimal difference)
   {
      this.differenceProperty().set(difference);
   }

   public SimpleObjectProperty<Calendar> paymentDateProperty()
   {
      if (paymentDate == null)
      {
         paymentDate = new SimpleObjectProperty<Calendar>();
      }
      return paymentDate;
   }

   public Calendar getPaymentDate()
   {
      return paymentDateProperty().get();
   }

   public final void setPaymentDate(Calendar paymentDate)
   {
      this.paymentDateProperty().set(paymentDate);
   }

   public SimpleObjectProperty<Calendar> recordDateProperty()
   {
      if (recordDate == null)
      {
         recordDate = new SimpleObjectProperty<Calendar>();
      }
      return recordDate;
   }

   public Calendar getRecordDate()
   {
      return recordDateProperty().get();
   }

   public final void setRecordDate(Calendar recordDate)
   {
      this.recordDateProperty().set(recordDate);
   }

   public SimpleObjectProperty<ObservableList<PaymentItem>> paymentItemsProperty()
   {
      if (paymentItems == null)
      {
         ObservableList<PaymentItem> observableArrayList = FXCollections.observableArrayList();
         paymentItems = new SimpleObjectProperty<ObservableList<PaymentItem>>(observableArrayList);
      }
      return paymentItems;
   }

   public List<PaymentItem> getPaymentItems()
   {
      return new ArrayList<PaymentItem>(paymentItemsProperty().get());
   }

   public final void setPaymentItems(List<PaymentItem> paymentItems)
   {
      this.paymentItemsProperty().get().clear();
      if (paymentItems != null)
         this.paymentItemsProperty().get().addAll(paymentItems);
   }

   public final void addToPaymentItems(PaymentItem entity)
   {
      this.paymentItemsProperty().get().add(entity);
   }

   public SimpleObjectProperty<PaymentAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<PaymentAgency>(new PaymentAgency());
      }
      return agency;
   }

   @NotNull(message = "Payment_agency_NotNull_validation")
   public PaymentAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(PaymentAgency agency)
   {
      if (agency == null)
      {
         agency = new PaymentAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
   }

   public SimpleObjectProperty<PaymentCashier> cashierProperty()
   {
      if (cashier == null)
      {
         cashier = new SimpleObjectProperty<PaymentCashier>(new PaymentCashier());
      }
      return cashier;
   }

   @NotNull(message = "Payment_cashier_NotNull_validation")
   public PaymentCashier getCashier()
   {
      return cashierProperty().get();
   }

   public final void setCashier(PaymentCashier cashier)
   {
      if (cashier == null)
      {
         cashier = new PaymentCashier();
      }
      PropertyReader.copy(cashier, getCashier());
      cashierProperty().setValue(ObjectUtils.clone(getCashier()));
   }

   public SimpleObjectProperty<PaymentCashDrawer> cashDrawerProperty()
   {
      if (cashDrawer == null)
      {
         cashDrawer = new SimpleObjectProperty<PaymentCashDrawer>(new PaymentCashDrawer());
      }
      return cashDrawer;
   }

   public PaymentCashDrawer getCashDrawer()
   {
      return cashDrawerProperty().get();
   }

   public final void setCashDrawer(PaymentCashDrawer cashDrawer)
   {
      if (cashDrawer == null)
      {
         cashDrawer = new PaymentCashDrawer();
      }
      PropertyReader.copy(cashDrawer, getCashDrawer());
      cashDrawerProperty().setValue(ObjectUtils.clone(getCashDrawer()));
   }

   public SimpleObjectProperty<ObservableList<PaymentCustomerInvoiceAssoc>> invoicesProperty()
   {
      if (invoices == null)
      {
         ObservableList<PaymentCustomerInvoiceAssoc> observableArrayList = FXCollections.observableArrayList();
         invoices = new SimpleObjectProperty<ObservableList<PaymentCustomerInvoiceAssoc>>(observableArrayList);
      }
      return invoices;
   }

   public List<PaymentCustomerInvoiceAssoc> getInvoices()
   {
      return new ArrayList<PaymentCustomerInvoiceAssoc>(invoicesProperty().get());
   }

   public final void setInvoices(List<PaymentCustomerInvoiceAssoc> invoices)
   {
      this.invoicesProperty().get().clear();
      if (invoices != null)
         this.invoicesProperty().get().addAll(invoices);
   }

   public final void addToInvoices(PaymentCustomerInvoiceAssoc entity)
   {
      this.invoicesProperty().get().add(entity);
   }

   public SimpleObjectProperty<PaymentPaidBy> paidByProperty()
   {
      if (paidBy == null)
      {
         paidBy = new SimpleObjectProperty<PaymentPaidBy>(new PaymentPaidBy());
      }
      return paidBy;
   }

   public PaymentPaidBy getPaidBy()
   {
      return paidByProperty().get();
   }

   public final void setPaidBy(PaymentPaidBy paidBy)
   {
      if (paidBy == null)
      {
         paidBy = new PaymentPaidBy();
      }
      PropertyReader.copy(paidBy, getPaidBy());
      paidByProperty().setValue(ObjectUtils.clone(getPaidBy()));
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
      Payment other = (Payment) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "paymentNumber");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
      ObservableList<PaymentItem> f = paymentItems.get();
      for (PaymentItem e : f)
      {
         e.setId(null);
         e.setVersion(0);
      }
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      Payment e = new Payment();
      e.id = id;
      e.version = version;

      e.paymentNumber = paymentNumber;
      e.paymentReceiptPrinted = paymentReceiptPrinted;
      e.paymentMode = paymentMode;
      e.amount = amount;
      e.receivedAmount = receivedAmount;
      e.difference = difference;
      e.paymentDate = paymentDate;
      e.recordDate = recordDate;
      e.paymentItems = paymentItems;
      e.agency = agency;
      e.cashier = cashier;
      e.cashDrawer = cashDrawer;
      e.invoices = invoices;
      e.paidBy = paidBy;
      return e;
   }
}