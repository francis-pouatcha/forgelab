package org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc;

import org.adorsys.adpharma.client.jpa.payment.Payment;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.relation.RelationshipTable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@RelationshipTable
public class PaymentCustomerInvoiceAssoc
{

   private Long id;
   private int version;

   @NotNull
   private SimpleStringProperty sourceQualifier;
   @NotNull
   private SimpleStringProperty targetQualifier;
   @NotNull
   private SimpleObjectProperty<Payment> source;
   @NotNull
   private SimpleObjectProperty<CustomerInvoice> target;

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

   public SimpleStringProperty sourceQualifierProperty()
   {
      if (sourceQualifier == null)
      {
         sourceQualifier = new SimpleStringProperty();
      }
      return sourceQualifier;
   }

   @NotNull
   public String getSourceQualifier()
   {
      return sourceQualifierProperty().get();
   }

   public final void setSourceQualifier(String sourceQualifier)
   {
      this.sourceQualifierProperty().set(sourceQualifier);
   }

   public SimpleStringProperty targetQualifierProperty()
   {
      if (targetQualifier == null)
      {
         targetQualifier = new SimpleStringProperty();
      }
      return targetQualifier;
   }

   public String getTargetQualifier()
   {
      return targetQualifierProperty().get();
   }

   public final void setTargetQualifier(String targetQualifier)
   {
      this.targetQualifierProperty().set(targetQualifier);
   }

   public SimpleObjectProperty<Payment> sourceProperty()
   {
      if (source == null)
      {
         source = new SimpleObjectProperty<Payment>(new Payment());
      }
      return source;
   }

   @NotNull
   public Payment getSource()
   {
      return sourceProperty().get();
   }

   public final void setSource(Payment source)
   {
      if (source == null)
      {
         source = new Payment();
      }
      PropertyReader.copy(source, getSource());
   }

   public SimpleObjectProperty<CustomerInvoice> targetProperty()
   {
      if (target == null)
      {
         target = new SimpleObjectProperty<CustomerInvoice>(new CustomerInvoice());
      }
      return target;
   }

   @NotNull
   public CustomerInvoice getTarget()
   {
      return targetProperty().get();
   }

   public final void setTarget(CustomerInvoice target)
   {
      if (target == null)
      {
         target = new CustomerInvoice();
      }
      PropertyReader.copy(target, getTarget());
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
      PaymentCustomerInvoiceAssoc other = (PaymentCustomerInvoiceAssoc) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "sourceQualifier", "targetQualifier", "source", "target");
   }
}