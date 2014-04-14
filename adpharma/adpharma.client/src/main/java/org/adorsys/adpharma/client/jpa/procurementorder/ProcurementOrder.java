package org.adorsys.adpharma.client.jpa.procurementorder;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerMode;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderType;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
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

import org.apache.commons.lang3.ObjectUtils;
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
@Description("ProcurementOrder_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("procurementOrderNumber")
@ListField({ "procurementOrderNumber", "poStatus", "agency.name",
      "amountBeforeTax", "amountAfterTax", "amountDiscount", "taxAmount",
      "netAmountToPay", "vat.rate" })
public class ProcurementOrder implements Cloneable
{

   private Long id;
   private int version;

   @Description("ProcurementOrder_procurementOrderNumber_description")
   private SimpleStringProperty procurementOrderNumber;
   @Description("ProcurementOrder_procmtOrderTriggerMode_description")
   private SimpleObjectProperty<ProcmtOrderTriggerMode> procmtOrderTriggerMode;
   @Description("ProcurementOrder_procurementOrderType_description")
   private SimpleObjectProperty<ProcurementOrderType> procurementOrderType;
   @Description("ProcurementOrder_poStatus_description")
   private SimpleObjectProperty<DocumentProcessingState> poStatus;
   @Description("ProcurementOrder_amountBeforeTax_description")
   private SimpleObjectProperty<BigDecimal> amountBeforeTax;
   @Description("ProcurementOrder_amountAfterTax_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountAfterTax;
   @Description("ProcurementOrder_amountDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountDiscount;
   @Description("ProcurementOrder_taxAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> taxAmount;
   @Description("ProcurementOrder_netAmountToPay_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> netAmountToPay;
   @Description("ProcurementOrder_submissionDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> submissionDate;
   @Description("ProcurementOrder_createdDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> createdDate;
   @Description("ProcurementOrder_procurementOrderItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = ProcurementOrderItem.class, selectionMode = SelectionMode.TABLE)
   private SimpleObjectProperty<ObservableList<ProcurementOrderItem>> procurementOrderItems;
   @Description("ProcurementOrder_creatingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<ProcurementOrderCreatingUser> creatingUser;
   @Description("ProcurementOrder_supplier_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Supplier.class)
   private SimpleObjectProperty<ProcurementOrderSupplier> supplier;
   @Description("ProcurementOrder_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<ProcurementOrderAgency> agency;
   @Description("ProcurementOrder_vat_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = VAT.class)
   private SimpleObjectProperty<ProcurementOrderVat> vat;

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

   public SimpleStringProperty procurementOrderNumberProperty()
   {
      if (procurementOrderNumber == null)
      {
         procurementOrderNumber = new SimpleStringProperty();
      }
      return procurementOrderNumber;
   }

   @NotNull(message = "ProcurementOrder_procurementOrderNumber_NotNull_validation")
   public String getProcurementOrderNumber()
   {
      return procurementOrderNumberProperty().get();
   }

   public final void setProcurementOrderNumber(String procurementOrderNumber)
   {
      this.procurementOrderNumberProperty().set(procurementOrderNumber);
   }

   public SimpleObjectProperty<ProcmtOrderTriggerMode> procmtOrderTriggerModeProperty()
   {
      if (procmtOrderTriggerMode == null)
      {
         procmtOrderTriggerMode = new SimpleObjectProperty<ProcmtOrderTriggerMode>();
      }
      return procmtOrderTriggerMode;
   }

   public ProcmtOrderTriggerMode getProcmtOrderTriggerMode()
   {
      return procmtOrderTriggerModeProperty().get();
   }

   public final void setProcmtOrderTriggerMode(ProcmtOrderTriggerMode procmtOrderTriggerMode)
   {
      this.procmtOrderTriggerModeProperty().set(procmtOrderTriggerMode);
   }

   public SimpleObjectProperty<ProcurementOrderType> procurementOrderTypeProperty()
   {
      if (procurementOrderType == null)
      {
         procurementOrderType = new SimpleObjectProperty<ProcurementOrderType>();
      }
      return procurementOrderType;
   }

   public ProcurementOrderType getProcurementOrderType()
   {
      return procurementOrderTypeProperty().get();
   }

   public final void setProcurementOrderType(ProcurementOrderType procurementOrderType)
   {
      this.procurementOrderTypeProperty().set(procurementOrderType);
   }

   public SimpleObjectProperty<DocumentProcessingState> poStatusProperty()
   {
      if (poStatus == null)
      {
         poStatus = new SimpleObjectProperty<DocumentProcessingState>();
      }
      return poStatus;
   }

   public DocumentProcessingState getPoStatus()
   {
      return poStatusProperty().get();
   }

   public final void setPoStatus(DocumentProcessingState poStatus)
   {
      this.poStatusProperty().set(poStatus);
   }

   public SimpleObjectProperty<BigDecimal> amountBeforeTaxProperty()
   {
      if (amountBeforeTax == null)
      {
         amountBeforeTax = new SimpleObjectProperty<BigDecimal>();
      }
      return amountBeforeTax;
   }

   @NotNull(message = "ProcurementOrder_amountBeforeTax_NotNull_validation")
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

   public SimpleObjectProperty<Calendar> submissionDateProperty()
   {
      if (submissionDate == null)
      {
         submissionDate = new SimpleObjectProperty<Calendar>();
      }
      return submissionDate;
   }

   public Calendar getSubmissionDate()
   {
      return submissionDateProperty().get();
   }

   public final void setSubmissionDate(Calendar submissionDate)
   {
      this.submissionDateProperty().set(submissionDate);
   }

   public SimpleObjectProperty<Calendar> createdDateProperty()
   {
      if (createdDate == null)
      {
         createdDate = new SimpleObjectProperty<Calendar>();
      }
      return createdDate;
   }

   public Calendar getCreatedDate()
   {
      return createdDateProperty().get();
   }

   public final void setCreatedDate(Calendar createdDate)
   {
      this.createdDateProperty().set(createdDate);
   }

   public SimpleObjectProperty<ObservableList<ProcurementOrderItem>> procurementOrderItemsProperty()
   {
      if (procurementOrderItems == null)
      {
         ObservableList<ProcurementOrderItem> observableArrayList = FXCollections.observableArrayList();
         procurementOrderItems = new SimpleObjectProperty<ObservableList<ProcurementOrderItem>>(observableArrayList);
      }
      return procurementOrderItems;
   }

   public List<ProcurementOrderItem> getProcurementOrderItems()
   {
      return new ArrayList<ProcurementOrderItem>(procurementOrderItemsProperty().get());
   }

   public final void setProcurementOrderItems(List<ProcurementOrderItem> procurementOrderItems)
   {
      this.procurementOrderItemsProperty().get().clear();
      if (procurementOrderItems != null)
         this.procurementOrderItemsProperty().get().addAll(procurementOrderItems);
   }

   public final void addToProcurementOrderItems(ProcurementOrderItem entity)
   {
      this.procurementOrderItemsProperty().get().add(entity);
   }

   public SimpleObjectProperty<ProcurementOrderCreatingUser> creatingUserProperty()
   {
      if (creatingUser == null)
      {
         creatingUser = new SimpleObjectProperty<ProcurementOrderCreatingUser>(new ProcurementOrderCreatingUser());
      }
      return creatingUser;
   }

   @NotNull(message = "ProcurementOrder_creatingUser_NotNull_validation")
   public ProcurementOrderCreatingUser getCreatingUser()
   {
      return creatingUserProperty().get();
   }

   public final void setCreatingUser(ProcurementOrderCreatingUser creatingUser)
   {
      if (creatingUser == null)
      {
         creatingUser = new ProcurementOrderCreatingUser();
      }
      PropertyReader.copy(creatingUser, getCreatingUser());
      creatingUserProperty().setValue(ObjectUtils.clone(getCreatingUser()));
   }

   public SimpleObjectProperty<ProcurementOrderSupplier> supplierProperty()
   {
      if (supplier == null)
      {
         supplier = new SimpleObjectProperty<ProcurementOrderSupplier>(new ProcurementOrderSupplier());
      }
      return supplier;
   }

   @NotNull(message = "ProcurementOrder_supplier_NotNull_validation")
   public ProcurementOrderSupplier getSupplier()
   {
      return supplierProperty().get();
   }

   public final void setSupplier(ProcurementOrderSupplier supplier)
   {
      if (supplier == null)
      {
         supplier = new ProcurementOrderSupplier();
      }
      PropertyReader.copy(supplier, getSupplier());
      supplierProperty().setValue(ObjectUtils.clone(getSupplier()));
   }

   public SimpleObjectProperty<ProcurementOrderAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<ProcurementOrderAgency>(new ProcurementOrderAgency());
      }
      return agency;
   }

   @NotNull(message = "ProcurementOrder_agency_NotNull_validation")
   public ProcurementOrderAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(ProcurementOrderAgency agency)
   {
      if (agency == null)
      {
         agency = new ProcurementOrderAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
   }

   public SimpleObjectProperty<ProcurementOrderVat> vatProperty()
   {
      if (vat == null)
      {
         vat = new SimpleObjectProperty<ProcurementOrderVat>(new ProcurementOrderVat());
      }
      return vat;
   }

   public ProcurementOrderVat getVat()
   {
      return vatProperty().get();
   }

   public final void setVat(ProcurementOrderVat vat)
   {
      if (vat == null)
      {
         vat = new ProcurementOrderVat();
      }
      PropertyReader.copy(vat, getVat());
      vatProperty().setValue(ObjectUtils.clone(getVat()));
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
      ProcurementOrder other = (ProcurementOrder) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "procurementOrderNumber");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
      ObservableList<ProcurementOrderItem> f = procurementOrderItems.get();
      for (ProcurementOrderItem e : f)
      {
         e.setId(null);
         e.setVersion(0);
      }
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      ProcurementOrder e = new ProcurementOrder();
      e.id = id;
      e.version = version;

      e.procurementOrderNumber = procurementOrderNumber;
      e.procmtOrderTriggerMode = procmtOrderTriggerMode;
      e.procurementOrderType = procurementOrderType;
      e.poStatus = poStatus;
      e.amountBeforeTax = amountBeforeTax;
      e.amountAfterTax = amountAfterTax;
      e.amountDiscount = amountDiscount;
      e.taxAmount = taxAmount;
      e.netAmountToPay = netAmountToPay;
      e.submissionDate = submissionDate;
      e.createdDate = createdDate;
      e.procurementOrderItems = procurementOrderItems;
      e.creatingUser = creatingUser;
      e.supplier = supplier;
      e.agency = agency;
      e.vat = vat;
      return e;
   }
}