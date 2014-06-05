package org.adorsys.adpharma.client.jpa.paymentitem;

import java.math.BigDecimal;
import java.util.Calendar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Payment_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentItemPayment implements Association<PaymentItem, Payment>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty paymentNumber;
   private SimpleBooleanProperty paymentReceiptPrinted;
   private SimpleObjectProperty<PaymentMode> paymentMode;
   private SimpleObjectProperty<BigDecimal> amount;
   private SimpleObjectProperty<BigDecimal> receivedAmount;
   private SimpleObjectProperty<BigDecimal> difference;
   private SimpleObjectProperty<Calendar> paymentDate;
   private SimpleObjectProperty<Calendar> recordDate;

   public PaymentItemPayment()
   {
   }

   public PaymentItemPayment(Payment entity)
   {
      PropertyReader.copy(entity, this);
   }

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
         paymentReceiptPrinted = new SimpleBooleanProperty();
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

   public SimpleObjectProperty<BigDecimal> differenceProperty()
   {
      if (difference == null)
      {
         difference = new SimpleObjectProperty<BigDecimal>();
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

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   //	@Override
   //	public boolean equals(Object obj) {
   //		if (this == obj)
   //			return true;
   //		if (obj == null)
   //			return false;
   //		if (getClass() != obj.getClass())
   //			return false;
   //		PaymentItemPayment other = (PaymentItemPayment) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "paymentNumber");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      PaymentItemPayment a = new PaymentItemPayment();
      a.id = id;
      a.version = version;

      a.paymentNumber = paymentNumber;
      a.paymentReceiptPrinted = paymentReceiptPrinted;
      a.paymentMode = paymentMode;
      a.amount = amount;
      a.receivedAmount = receivedAmount;
      a.difference = difference;
      a.paymentDate = paymentDate;
      a.recordDate = recordDate;
      return a;
   }

}
