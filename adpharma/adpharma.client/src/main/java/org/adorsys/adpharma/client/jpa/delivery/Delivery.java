package org.adorsys.adpharma.client.jpa.delivery;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Delivery_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("deliveryNumber")
@ListField({ "deliveryNumber", "deliverySlipNumber", "dateOnDeliverySlip",
      "amountBeforeTax", "amountAfterTax", "amountDiscount",
      "netAmountToPay", "vat.rate", "receivingAgency.name" })
public class Delivery
{

   private Long id;
   private int version;

   @Description("Delivery_deliveryNumber_description")
   private SimpleStringProperty deliveryNumber;
   @Description("Delivery_deliverySlipNumber_description")
   private SimpleStringProperty deliverySlipNumber;
   @Description("Delivery_deliveryProcessingState_description")
   private SimpleObjectProperty<DocumentProcessingState> deliveryProcessingState;
   @Description("Delivery_amountBeforeTax_description")
   private SimpleObjectProperty<BigDecimal> amountBeforeTax;
   @Description("Delivery_amountAfterTax_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountAfterTax;
   @Description("Delivery_amountDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountDiscount;
   @Description("Delivery_netAmountToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> netAmountToPay;
   @Description("Delivery_dateOnDeliverySlip_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> dateOnDeliverySlip;
   @Description("Delivery_orderDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> orderDate;
   @Description("Delivery_deliveryDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> deliveryDate;
   @Description("Delivery_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Description("Delivery_deliveryItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = DeliveryItem.class, selectionMode = SelectionMode.TABLE)
   private SimpleObjectProperty<ObservableList<DeliveryItem>> deliveryItems;
   @Description("Delivery_creatingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<DeliveryCreatingUser> creatingUser;
   @Description("Delivery_supplier_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Supplier.class)
   private SimpleObjectProperty<DeliverySupplier> supplier;
   @Description("Delivery_vat_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = VAT.class)
   private SimpleObjectProperty<DeliveryVat> vat;
   @Description("Delivery_currency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Currency.class)
   private SimpleObjectProperty<DeliveryCurrency> currency;
   @Description("Delivery_receivingAgency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<DeliveryReceivingAgency> receivingAgency;

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

   @NotNull(message = "Delivery_deliveryNumber_NotNull_validation")
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

   @NotNull(message = "Delivery_deliverySlipNumber_NotNull_validation")
   public String getDeliverySlipNumber()
   {
      return deliverySlipNumberProperty().get();
   }

   public final void setDeliverySlipNumber(String deliverySlipNumber)
   {
      this.deliverySlipNumberProperty().set(deliverySlipNumber);
   }

   public SimpleObjectProperty<DocumentProcessingState> deliveryProcessingStateProperty()
   {
      if (deliveryProcessingState == null)
      {
         deliveryProcessingState = new SimpleObjectProperty<DocumentProcessingState>();
      }
      return deliveryProcessingState;
   }

   public DocumentProcessingState getDeliveryProcessingState()
   {
      return deliveryProcessingStateProperty().get();
   }

   public final void setDeliveryProcessingState(DocumentProcessingState deliveryProcessingState)
   {
      this.deliveryProcessingStateProperty().set(deliveryProcessingState);
   }

   public SimpleObjectProperty<BigDecimal> amountBeforeTaxProperty()
   {
      if (amountBeforeTax == null)
      {
         amountBeforeTax = new SimpleObjectProperty<BigDecimal>();
      }
      return amountBeforeTax;
   }

   @NotNull(message = "Delivery_amountBeforeTax_NotNull_validation")
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

   public SimpleObjectProperty<Calendar> orderDateProperty()
   {
      if (orderDate == null)
      {
         orderDate = new SimpleObjectProperty<Calendar>();
      }
      return orderDate;
   }

   public Calendar getOrderDate()
   {
      return orderDateProperty().get();
   }

   public final void setOrderDate(Calendar orderDate)
   {
      this.orderDateProperty().set(orderDate);
   }

   public SimpleObjectProperty<Calendar> deliveryDateProperty()
   {
      if (deliveryDate == null)
      {
         deliveryDate = new SimpleObjectProperty<Calendar>();
      }
      return deliveryDate;
   }

   public Calendar getDeliveryDate()
   {
      return deliveryDateProperty().get();
   }

   public final void setDeliveryDate(Calendar deliveryDate)
   {
      this.deliveryDateProperty().set(deliveryDate);
   }

   public SimpleObjectProperty<Calendar> recordingDateProperty()
   {
      if (recordingDate == null)
      {
         recordingDate = new SimpleObjectProperty<Calendar>();
      }
      return recordingDate;
   }

   public Calendar getRecordingDate()
   {
      return recordingDateProperty().get();
   }

   public final void setRecordingDate(Calendar recordingDate)
   {
      this.recordingDateProperty().set(recordingDate);
   }

   public SimpleObjectProperty<ObservableList<DeliveryItem>> deliveryItemsProperty()
   {
      if (deliveryItems == null)
      {
         ObservableList<DeliveryItem> observableArrayList = FXCollections.observableArrayList();
         deliveryItems = new SimpleObjectProperty<ObservableList<DeliveryItem>>(observableArrayList);
      }
      return deliveryItems;
   }

   public List<DeliveryItem> getDeliveryItems()
   {
      return new ArrayList<DeliveryItem>(deliveryItemsProperty().get());
   }

   public final void setDeliveryItems(List<DeliveryItem> deliveryItems)
   {
      this.deliveryItemsProperty().get().clear();
      if (deliveryItems != null)
         this.deliveryItemsProperty().get().addAll(deliveryItems);
   }

   public final void addToDeliveryItems(DeliveryItem entity)
   {
      this.deliveryItemsProperty().get().add(entity);
   }

   public SimpleObjectProperty<DeliveryCreatingUser> creatingUserProperty()
   {
      if (creatingUser == null)
      {
         creatingUser = new SimpleObjectProperty<DeliveryCreatingUser>(new DeliveryCreatingUser());
      }
      return creatingUser;
   }

   @NotNull(message = "Delivery_creatingUser_NotNull_validation")
   public DeliveryCreatingUser getCreatingUser()
   {
      return creatingUserProperty().get();
   }

   public final void setCreatingUser(DeliveryCreatingUser creatingUser)
   {
      if (creatingUser == null)
      {
         creatingUser = new DeliveryCreatingUser();
      }
      PropertyReader.copy(creatingUser, getCreatingUser());
   }

   public SimpleObjectProperty<DeliverySupplier> supplierProperty()
   {
      if (supplier == null)
      {
         supplier = new SimpleObjectProperty<DeliverySupplier>(new DeliverySupplier());
      }
      return supplier;
   }

   @NotNull(message = "Delivery_supplier_NotNull_validation")
   public DeliverySupplier getSupplier()
   {
      return supplierProperty().get();
   }

   public final void setSupplier(DeliverySupplier supplier)
   {
      if (supplier == null)
      {
         supplier = new DeliverySupplier();
      }
      PropertyReader.copy(supplier, getSupplier());
   }

   public SimpleObjectProperty<DeliveryVat> vatProperty()
   {
      if (vat == null)
      {
         vat = new SimpleObjectProperty<DeliveryVat>(new DeliveryVat());
      }
      return vat;
   }

   public DeliveryVat getVat()
   {
      return vatProperty().get();
   }

   public final void setVat(DeliveryVat vat)
   {
      if (vat == null)
      {
         vat = new DeliveryVat();
      }
      PropertyReader.copy(vat, getVat());
   }

   public SimpleObjectProperty<DeliveryCurrency> currencyProperty()
   {
      if (currency == null)
      {
         currency = new SimpleObjectProperty<DeliveryCurrency>(new DeliveryCurrency());
      }
      return currency;
   }

   public DeliveryCurrency getCurrency()
   {
      return currencyProperty().get();
   }

   public final void setCurrency(DeliveryCurrency currency)
   {
      if (currency == null)
      {
         currency = new DeliveryCurrency();
      }
      PropertyReader.copy(currency, getCurrency());
   }

   public SimpleObjectProperty<DeliveryReceivingAgency> receivingAgencyProperty()
   {
      if (receivingAgency == null)
      {
         receivingAgency = new SimpleObjectProperty<DeliveryReceivingAgency>(new DeliveryReceivingAgency());
      }
      return receivingAgency;
   }

   @NotNull(message = "Delivery_receivingAgency_NotNull_validation")
   public DeliveryReceivingAgency getReceivingAgency()
   {
      return receivingAgencyProperty().get();
   }

   public final void setReceivingAgency(DeliveryReceivingAgency receivingAgency)
   {
      if (receivingAgency == null)
      {
         receivingAgency = new DeliveryReceivingAgency();
      }
      PropertyReader.copy(receivingAgency, getReceivingAgency());
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
      Delivery other = (Delivery) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "deliveryNumber");
   }
}