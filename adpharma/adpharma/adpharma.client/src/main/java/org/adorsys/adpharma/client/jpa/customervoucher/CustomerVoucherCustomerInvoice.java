package org.adorsys.adpharma.client.jpa.customervoucher;

import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCustomer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInsurance;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUser;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceAgency;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrder;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;
import org.adorsys.adpharma.client.jpa.payment.Payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("CustomerInvoice_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerVoucherCustomerInvoice implements Association<CustomerVoucher, CustomerInvoice>
{

   private Long id;
   private int version;

   private SimpleStringProperty invoiceNumber;
   private SimpleBooleanProperty settled;
   private SimpleBooleanProperty cashed;
   private SimpleObjectProperty<InvoiceType> invoiceType;
   private SimpleObjectProperty<BigDecimal> amountBeforeTax;
   private SimpleObjectProperty<BigDecimal> amountVAT;
   private SimpleObjectProperty<BigDecimal> amountDiscount;
   private SimpleObjectProperty<BigDecimal> amountAfterTax;
   private SimpleObjectProperty<BigDecimal> netToPay;
   private SimpleObjectProperty<BigDecimal> customerRestTopay;
   private SimpleObjectProperty<BigDecimal> insurranceRestTopay;
   private SimpleObjectProperty<BigDecimal> advancePayment;
   private SimpleObjectProperty<BigDecimal> totalRestToPay;
   private SimpleObjectProperty<Calendar> creationDate;

   public CustomerVoucherCustomerInvoice()
   {
   }

   public CustomerVoucherCustomerInvoice(CustomerInvoice entity)
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

   public SimpleStringProperty invoiceNumberProperty()
   {
      if (invoiceNumber == null)
      {
         invoiceNumber = new SimpleStringProperty();
      }
      return invoiceNumber;
   }

   public String getInvoiceNumber()
   {
      return invoiceNumberProperty().get();
   }

   public final void setInvoiceNumber(String invoiceNumber)
   {
      this.invoiceNumberProperty().set(invoiceNumber);
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

   public SimpleBooleanProperty cashedProperty()
   {
      if (cashed == null)
      {
         cashed = new SimpleBooleanProperty();
      }
      return cashed;
   }

   public Boolean getCashed()
   {
      return cashedProperty().get();
   }

   public final void setCashed(Boolean cashed)
   {
      if (cashed == null)
         cashed = Boolean.FALSE;
      this.cashedProperty().set(cashed);
   }

   public SimpleObjectProperty<InvoiceType> invoiceTypeProperty()
   {
      if (invoiceType == null)
      {
         invoiceType = new SimpleObjectProperty<InvoiceType>();
      }
      return invoiceType;
   }

   public InvoiceType getInvoiceType()
   {
      return invoiceTypeProperty().get();
   }

   public final void setInvoiceType(InvoiceType invoiceType)
   {
      this.invoiceTypeProperty().set(invoiceType);
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

   public SimpleObjectProperty<BigDecimal> amountVATProperty()
   {
      if (amountVAT == null)
      {
         amountVAT = new SimpleObjectProperty<BigDecimal>();
      }
      return amountVAT;
   }

   public BigDecimal getAmountVAT()
   {
      return amountVATProperty().get();
   }

   public final void setAmountVAT(BigDecimal amountVAT)
   {
      this.amountVATProperty().set(amountVAT);
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

   public SimpleObjectProperty<BigDecimal> netToPayProperty()
   {
      if (netToPay == null)
      {
         netToPay = new SimpleObjectProperty<BigDecimal>();
      }
      return netToPay;
   }

   public BigDecimal getNetToPay()
   {
      return netToPayProperty().get();
   }

   public final void setNetToPay(BigDecimal netToPay)
   {
      this.netToPayProperty().set(netToPay);
   }

   public SimpleObjectProperty<BigDecimal> customerRestTopayProperty()
   {
      if (customerRestTopay == null)
      {
         customerRestTopay = new SimpleObjectProperty<BigDecimal>();
      }
      return customerRestTopay;
   }

   public BigDecimal getCustomerRestTopay()
   {
      return customerRestTopayProperty().get();
   }

   public final void setCustomerRestTopay(BigDecimal customerRestTopay)
   {
      this.customerRestTopayProperty().set(customerRestTopay);
   }

   public SimpleObjectProperty<BigDecimal> insurranceRestTopayProperty()
   {
      if (insurranceRestTopay == null)
      {
         insurranceRestTopay = new SimpleObjectProperty<BigDecimal>();
      }
      return insurranceRestTopay;
   }

   public BigDecimal getInsurranceRestTopay()
   {
      return insurranceRestTopayProperty().get();
   }

   public final void setInsurranceRestTopay(BigDecimal insurranceRestTopay)
   {
      this.insurranceRestTopayProperty().set(insurranceRestTopay);
   }

   public SimpleObjectProperty<BigDecimal> advancePaymentProperty()
   {
      if (advancePayment == null)
      {
         advancePayment = new SimpleObjectProperty<BigDecimal>();
      }
      return advancePayment;
   }

   public BigDecimal getAdvancePayment()
   {
      return advancePaymentProperty().get();
   }

   public final void setAdvancePayment(BigDecimal advancePayment)
   {
      this.advancePaymentProperty().set(advancePayment);
   }

   public SimpleObjectProperty<BigDecimal> totalRestToPayProperty()
   {
      if (totalRestToPay == null)
      {
         totalRestToPay = new SimpleObjectProperty<BigDecimal>();
      }
      return totalRestToPay;
   }

   public BigDecimal getTotalRestToPay()
   {
      return totalRestToPayProperty().get();
   }

   public final void setTotalRestToPay(BigDecimal totalRestToPay)
   {
      this.totalRestToPayProperty().set(totalRestToPay);
   }

   public SimpleObjectProperty<Calendar> creationDateProperty()
   {
      if (creationDate == null)
      {
         creationDate = new SimpleObjectProperty<Calendar>();
      }
      return creationDate;
   }

   public Calendar getCreationDate()
   {
      return creationDateProperty().get();
   }

   public final void setCreationDate(Calendar creationDate)
   {
      this.creationDateProperty().set(creationDate);
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
      CustomerVoucherCustomerInvoice other = (CustomerVoucherCustomerInvoice) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "invoiceNumber");
   }
}