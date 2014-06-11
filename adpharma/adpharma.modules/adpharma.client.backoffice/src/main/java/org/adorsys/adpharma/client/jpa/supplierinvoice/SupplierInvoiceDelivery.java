package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.math.BigDecimal;
import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Delivery_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierInvoiceDelivery implements Association<SupplierInvoice, Delivery>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty deliveryNumber;
   private SimpleStringProperty deliverySlipNumber;
   private SimpleObjectProperty<BigDecimal> amountBeforeTax;
   private SimpleObjectProperty<BigDecimal> amountAfterTax;
   private SimpleObjectProperty<BigDecimal> amountDiscount;
   private SimpleObjectProperty<BigDecimal> netAmountToPay;
   private SimpleObjectProperty<Calendar> dateOnDeliverySlip;

   public SupplierInvoiceDelivery()
   {
   }

   public SupplierInvoiceDelivery(Delivery entity)
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

   public SimpleStringProperty deliveryNumberProperty()
   {
      if (deliveryNumber == null)
      {
         deliveryNumber = new SimpleStringProperty();
      }
      return deliveryNumber;
   }

   public String getDeliveryNumber()
   {
      return deliveryNumberProperty().get();
   }

   public final void setDeliveryNumber(String deliveryNumber)
   {
      this.deliveryNumberProperty().set(deliveryNumber);
   }

   public SimpleStringProperty deliverySlipNumberProperty()
   {
      if (deliverySlipNumber == null)
      {
         deliverySlipNumber = new SimpleStringProperty();
      }
      return deliverySlipNumber;
   }

   public String getDeliverySlipNumber()
   {
      return deliverySlipNumberProperty().get();
   }

   public final void setDeliverySlipNumber(String deliverySlipNumber)
   {
      this.deliverySlipNumberProperty().set(deliverySlipNumber);
   }

   public SimpleObjectProperty<BigDecimal> amountBeforeTaxProperty()
   {
      if (amountBeforeTax == null)
      {
         amountBeforeTax = new SimpleObjectProperty<BigDecimal>();
      }
      return amountBeforeTax;
   }

   public BigDecimal getAmountBeforeTax()
   {
      return amountBeforeTaxProperty().get();
   }

   public final void setAmountBeforeTax(BigDecimal amountBeforeTax)
   {
      this.amountBeforeTaxProperty().set(amountBeforeTax);
   }

   public SimpleObjectProperty<BigDecimal> amountAfterTaxProperty()
   {
      if (amountAfterTax == null)
      {
         amountAfterTax = new SimpleObjectProperty<BigDecimal>();
      }
      return amountAfterTax;
   }

   public BigDecimal getAmountAfterTax()
   {
      return amountAfterTaxProperty().get();
   }

   public final void setAmountAfterTax(BigDecimal amountAfterTax)
   {
      this.amountAfterTaxProperty().set(amountAfterTax);
   }

   public SimpleObjectProperty<BigDecimal> amountDiscountProperty()
   {
      if (amountDiscount == null)
      {
         amountDiscount = new SimpleObjectProperty<BigDecimal>();
      }
      return amountDiscount;
   }

   public BigDecimal getAmountDiscount()
   {
      return amountDiscountProperty().get();
   }

   public final void setAmountDiscount(BigDecimal amountDiscount)
   {
      this.amountDiscountProperty().set(amountDiscount);
   }

   public SimpleObjectProperty<BigDecimal> netAmountToPayProperty()
   {
      if (netAmountToPay == null)
      {
         netAmountToPay = new SimpleObjectProperty<BigDecimal>();
      }
      return netAmountToPay;
   }

   public BigDecimal getNetAmountToPay()
   {
      return netAmountToPayProperty().get();
   }

   public final void setNetAmountToPay(BigDecimal netAmountToPay)
   {
      this.netAmountToPayProperty().set(netAmountToPay);
   }

   public SimpleObjectProperty<Calendar> dateOnDeliverySlipProperty()
   {
      if (dateOnDeliverySlip == null)
      {
         dateOnDeliverySlip = new SimpleObjectProperty<Calendar>();
      }
      return dateOnDeliverySlip;
   }

   public Calendar getDateOnDeliverySlip()
   {
      return dateOnDeliverySlipProperty().get();
   }

   public final void setDateOnDeliverySlip(Calendar dateOnDeliverySlip)
   {
      this.dateOnDeliverySlipProperty().set(dateOnDeliverySlip);
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
   //		SupplierInvoiceDelivery other = (SupplierInvoiceDelivery) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "deliveryNumber");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      SupplierInvoiceDelivery a = new SupplierInvoiceDelivery();
      a.id = id;
      a.version = version;

      a.deliveryNumber = deliveryNumber;
      a.deliverySlipNumber = deliverySlipNumber;
      a.amountBeforeTax = amountBeforeTax;
      a.amountAfterTax = amountAfterTax;
      a.amountDiscount = amountDiscount;
      a.netAmountToPay = netAmountToPay;
      a.dateOnDeliverySlip = dateOnDeliverySlip;
      return a;
   }

}