package org.adorsys.adpharma.client.jpa.paymentitem;

import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("PaymentItem_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "paymentMode", "documentNumber", "amount", "receivedAmount",
      "paidBy.fullName" })
@ToStringField("documentNumber")
public class PaymentItem implements Cloneable
{

   private Long id;
   private int version;

   @Description("PaymentItem_documentNumber_description")
   private SimpleStringProperty documentNumber;
   @Description("PaymentItem_documentDetails_description")
   private SimpleStringProperty documentDetails;
   @Description("PaymentItem_paymentMode_description")
   private SimpleObjectProperty<PaymentMode> paymentMode;
   @Description("PaymentItem_amount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amount;
   @Description("PaymentItem_receivedAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> receivedAmount;
   @Description("PaymentItem_payment_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = Payment.class)
   private SimpleObjectProperty<PaymentItemPayment> payment;
   @Description("PaymentItem_paidBy_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private SimpleObjectProperty<PaymentItemPaidBy> paidBy;

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

   public SimpleStringProperty documentNumberProperty()
   {
      if (documentNumber == null)
      {
         documentNumber = new SimpleStringProperty();
      }
      return documentNumber;
   }

   public String getDocumentNumber()
   {
      return documentNumberProperty().get();
   }

   public final void setDocumentNumber(String documentNumber)
   {
      this.documentNumberProperty().set(documentNumber);
   }

   public SimpleStringProperty documentDetailsProperty()
   {
      if (documentDetails == null)
      {
         documentDetails = new SimpleStringProperty();
      }
      return documentDetails;
   }

   public String getDocumentDetails()
   {
      return documentDetailsProperty().get();
   }

   public final void setDocumentDetails(String documentDetails)
   {
      this.documentDetailsProperty().set(documentDetails);
   }

   public SimpleObjectProperty<PaymentMode> paymentModeProperty()
   {
      if (paymentMode == null)
      {
         paymentMode = new SimpleObjectProperty<PaymentMode>();
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
         amount = new SimpleObjectProperty<BigDecimal>();
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
         receivedAmount = new SimpleObjectProperty<BigDecimal>();
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

   public SimpleObjectProperty<PaymentItemPayment> paymentProperty()
   {
      if (payment == null)
      {
         payment = new SimpleObjectProperty<PaymentItemPayment>(new PaymentItemPayment());
      }
      return payment;
   }

   public PaymentItemPayment getPayment()
   {
      return paymentProperty().get();
   }

   public final void setPayment(PaymentItemPayment payment)
   {
      if (payment == null)
      {
         payment = new PaymentItemPayment();
      }
      PropertyReader.copy(payment, getPayment());
      paymentProperty().setValue(ObjectUtils.clone(getPayment()));
   }

   public SimpleObjectProperty<PaymentItemPaidBy> paidByProperty()
   {
      if (paidBy == null)
      {
         paidBy = new SimpleObjectProperty<PaymentItemPaidBy>(new PaymentItemPaidBy());
      }
      return paidBy;
   }

   public PaymentItemPaidBy getPaidBy()
   {
      return paidByProperty().get();
   }

   public final void setPaidBy(PaymentItemPaidBy paidBy)
   {
      if (paidBy == null)
      {
         paidBy = new PaymentItemPaidBy();
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
      PaymentItem other = (PaymentItem) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "documentNumber");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
      PaymentItemPayment f = payment.get();
      f.setId(null);
      f.setVersion(0);
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      PaymentItem e = new PaymentItem();
      e.id = id;
      e.version = version;

      e.documentNumber = documentNumber;
      e.documentDetails = documentDetails;
      e.paymentMode = paymentMode;
      e.amount = amount;
      e.receivedAmount = receivedAmount;
      e.payment = payment;
      e.paidBy = paidBy;
      return e;
   }
}