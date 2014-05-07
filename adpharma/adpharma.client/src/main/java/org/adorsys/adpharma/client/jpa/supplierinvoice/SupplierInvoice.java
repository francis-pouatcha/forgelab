package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceType;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplierinvoiceitem.SupplierInvoiceItem;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("SupplierInvoice_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "invoiceType", "invoiceNumber", "creationDate", "supplier.name",
      "creatingUser.fullName", "agency.name", "delivery.deliveryNumber",
      "settled", "amountBeforeTax", "taxAmount", "amountDiscount",
      "amountAfterTax", "netToPay", "advancePayment", "totalRestToPay" })
@ToStringField("invoiceNumber")
public class SupplierInvoice implements Cloneable
{

   private Long id;
   private int version;

   @Description("SupplierInvoice_invoiceNumber_description")
   private SimpleStringProperty invoiceNumber;
   @Description("SupplierInvoice_settled_description")
   private SimpleBooleanProperty settled;
   @Description("SupplierInvoice_invoiceType_description")
   private SimpleObjectProperty<InvoiceType> invoiceType;
   @Description("SupplierInvoice_amountBeforeTax_description")
   private SimpleObjectProperty<BigDecimal> amountBeforeTax;
   @Description("SupplierInvoice_taxAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> taxAmount;
   @Description("SupplierInvoice_amountDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountDiscount;
   @Description("SupplierInvoice_amountAfterTax_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountAfterTax;
   @Description("SupplierInvoice_netToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> netToPay;
   @Description("SupplierInvoice_advancePayment_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> advancePayment;
   @Description("SupplierInvoice_totalRestToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalRestToPay;
   @Description("SupplierInvoice_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> creationDate;
   @Description("SupplierInvoice_invoiceItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = SupplierInvoiceItem.class, selectionMode = SelectionMode.TABLE)
   private SimpleObjectProperty<ObservableList<SupplierInvoiceItem>> invoiceItems;
   @Description("SupplierInvoice_supplier_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Supplier.class)
   private SimpleObjectProperty<SupplierInvoiceSupplier> supplier;
   @Description("SupplierInvoice_creatingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<SupplierInvoiceCreatingUser> creatingUser;
   @Description("SupplierInvoice_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<SupplierInvoiceAgency> agency;
   @Description("SupplierInvoice_delivery_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Delivery.class)
   private SimpleObjectProperty<SupplierInvoiceDelivery> delivery;

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

   public SimpleObjectProperty<BigDecimal> taxAmountProperty()
   {
      if (taxAmount == null)
      {
         taxAmount = new SimpleObjectProperty<BigDecimal>();
      }
      return taxAmount;
   }

   public BigDecimal getTaxAmount()
   {
      return taxAmountProperty().get();
   }

   public final void setTaxAmount(BigDecimal taxAmount)
   {
      this.taxAmountProperty().set(taxAmount);
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

   public SimpleObjectProperty<ObservableList<SupplierInvoiceItem>> invoiceItemsProperty()
   {
      if (invoiceItems == null)
      {
         ObservableList<SupplierInvoiceItem> observableArrayList = FXCollections.observableArrayList();
         invoiceItems = new SimpleObjectProperty<ObservableList<SupplierInvoiceItem>>(observableArrayList);
      }
      return invoiceItems;
   }

   public List<SupplierInvoiceItem> getInvoiceItems()
   {
      return new ArrayList<SupplierInvoiceItem>(invoiceItemsProperty().get());
   }

   public final void setInvoiceItems(List<SupplierInvoiceItem> invoiceItems)
   {
      this.invoiceItemsProperty().get().clear();
      if (invoiceItems != null)
         this.invoiceItemsProperty().get().addAll(invoiceItems);
   }

   public final void addToInvoiceItems(SupplierInvoiceItem entity)
   {
      this.invoiceItemsProperty().get().add(entity);
   }

   public SimpleObjectProperty<SupplierInvoiceSupplier> supplierProperty()
   {
      if (supplier == null)
      {
         supplier = new SimpleObjectProperty<SupplierInvoiceSupplier>(new SupplierInvoiceSupplier());
      }
      return supplier;
   }

   @NotNull(message = "SupplierInvoice_supplier_NotNull_validation")
   public SupplierInvoiceSupplier getSupplier()
   {
      return supplierProperty().get();
   }

   public final void setSupplier(SupplierInvoiceSupplier supplier)
   {
      if (supplier == null)
      {
         supplier = new SupplierInvoiceSupplier();
      }
      PropertyReader.copy(supplier, getSupplier());
      supplierProperty().setValue(ObjectUtils.clone(getSupplier()));
   }

   public SimpleObjectProperty<SupplierInvoiceCreatingUser> creatingUserProperty()
   {
      if (creatingUser == null)
      {
         creatingUser = new SimpleObjectProperty<SupplierInvoiceCreatingUser>(new SupplierInvoiceCreatingUser());
      }
      return creatingUser;
   }

   @NotNull(message = "SupplierInvoice_creatingUser_NotNull_validation")
   public SupplierInvoiceCreatingUser getCreatingUser()
   {
      return creatingUserProperty().get();
   }

   public final void setCreatingUser(SupplierInvoiceCreatingUser creatingUser)
   {
      if (creatingUser == null)
      {
         creatingUser = new SupplierInvoiceCreatingUser();
      }
      PropertyReader.copy(creatingUser, getCreatingUser());
      creatingUserProperty().setValue(ObjectUtils.clone(getCreatingUser()));
   }

   public SimpleObjectProperty<SupplierInvoiceAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<SupplierInvoiceAgency>(new SupplierInvoiceAgency());
      }
      return agency;
   }

   @NotNull(message = "SupplierInvoice_agency_NotNull_validation")
   public SupplierInvoiceAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(SupplierInvoiceAgency agency)
   {
      if (agency == null)
      {
         agency = new SupplierInvoiceAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
   }

   public SimpleObjectProperty<SupplierInvoiceDelivery> deliveryProperty()
   {
      if (delivery == null)
      {
         delivery = new SimpleObjectProperty<SupplierInvoiceDelivery>(new SupplierInvoiceDelivery());
      }
      return delivery;
   }

   public SupplierInvoiceDelivery getDelivery()
   {
      return deliveryProperty().get();
   }

   public final void setDelivery(SupplierInvoiceDelivery delivery)
   {
      if (delivery == null)
      {
         delivery = new SupplierInvoiceDelivery();
      }
      PropertyReader.copy(delivery, getDelivery());
      deliveryProperty().setValue(ObjectUtils.clone(getDelivery()));
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
      SupplierInvoice other = (SupplierInvoice) obj;
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

   public void cleanIds()
   {
      id = null;
      version = 0;
      ObservableList<SupplierInvoiceItem> f = invoiceItems.get();
      for (SupplierInvoiceItem e : f)
      {
         e.setId(null);
         e.setVersion(0);
      }
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      SupplierInvoice e = new SupplierInvoice();
      e.id = id;
      e.version = version;

      e.invoiceNumber = invoiceNumber;
      e.settled = settled;
      e.invoiceType = invoiceType;
      e.amountBeforeTax = amountBeforeTax;
      e.taxAmount = taxAmount;
      e.amountDiscount = amountDiscount;
      e.amountAfterTax = amountAfterTax;
      e.netToPay = netToPay;
      e.advancePayment = advancePayment;
      e.totalRestToPay = totalRestToPay;
      e.creationDate = creationDate;
      e.invoiceItems = invoiceItems;
      e.supplier = supplier;
      e.creatingUser = creatingUser;
      e.agency = agency;
      e.delivery = delivery;
      return e;
   }
}