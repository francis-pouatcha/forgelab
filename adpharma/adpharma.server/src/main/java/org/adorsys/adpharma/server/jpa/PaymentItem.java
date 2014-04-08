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
import org.adorsys.adpharma.server.jpa.PaymentMode;
import javax.persistence.Enumerated;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.Customer;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.adpharma.server.jpa.Payment;

@Entity
@Description("PaymentItem_description")
@ListField({ "paymentMode", "documentNumber", "amount", "receivedAmount",
      "paidBy.fullName" })
@ToStringField("documentNumber")
public class PaymentItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("PaymentItem_paymentMode_description")
   @Enumerated
   private PaymentMode paymentMode;

   @Column
   @Description("PaymentItem_documentNumber_description")
   private String documentNumber;

   @Column
   @Description("PaymentItem_documentDetails_description")
   private String documentDetails;

   @Column
   @Description("PaymentItem_amount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal amount;

   @Column
   @Description("PaymentItem_receivedAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal receivedAmount;

   @ManyToOne
   @Description("PaymentItem_paidBy_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private Customer paidBy;

   @ManyToOne
   @Description("PaymentItem_payment_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = Payment.class)
   private Payment payment;

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
         return id.equals(((PaymentItem) that).id);
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

   public PaymentMode getPaymentMode()
   {
      return this.paymentMode;
   }

   public void setPaymentMode(final PaymentMode paymentMode)
   {
      this.paymentMode = paymentMode;
   }

   public String getDocumentNumber()
   {
      return this.documentNumber;
   }

   public void setDocumentNumber(final String documentNumber)
   {
      this.documentNumber = documentNumber;
   }

   public String getDocumentDetails()
   {
      return this.documentDetails;
   }

   public void setDocumentDetails(final String documentDetails)
   {
      this.documentDetails = documentDetails;
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

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (documentNumber != null && !documentNumber.trim().isEmpty())
         result += "documentNumber: " + documentNumber;
      if (documentDetails != null && !documentDetails.trim().isEmpty())
         result += ", documentDetails: " + documentDetails;
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

   public Payment getPayment()
   {
      return this.payment;
   }

   public void setPayment(final Payment payment)
   {
      this.payment = payment;
   }
}