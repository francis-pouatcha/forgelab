package org.adorsys.adpharma.client.jpa.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.adpharma.client.jpa.login.Login;
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
@Description("Inventory_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("inventoryNumber")
@ListField({ "inventoryNumber", "gapSaleAmount", "gapPurchaseAmount",
      "inventoryStatus", "agency.name" })
public class Inventory implements Cloneable
{

   private Long id;
   private int version;

   @Description("Inventory_inventoryNumber_description")
   private SimpleStringProperty inventoryNumber;
   @Description("Inventory_description_description")
   private SimpleStringProperty description;
   @Description("Inventory_inventoryStatus_description")
   private SimpleObjectProperty<DocumentProcessingState> inventoryStatus;
   @Description("Inventory_gapSaleAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> gapSaleAmount;
   @Description("Inventory_gapPurchaseAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> gapPurchaseAmount;
   @Description("Inventory_inventoryDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> inventoryDate;
   @Description("Inventory_inventoryItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = InventoryItem.class, selectionMode = SelectionMode.TABLE)
   private SimpleObjectProperty<ObservableList<InventoryItem>> inventoryItems;
   @Description("Inventory_recordingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<InventoryRecordingUser> recordingUser;
   @Description("Inventory_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<InventoryAgency> agency;

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

   public SimpleStringProperty inventoryNumberProperty()
   {
      if (inventoryNumber == null)
      {
         inventoryNumber = new SimpleStringProperty();
      }
      return inventoryNumber;
   }

   public String getInventoryNumber()
   {
      return inventoryNumberProperty().get();
   }

   public final void setInventoryNumber(String inventoryNumber)
   {
      this.inventoryNumberProperty().set(inventoryNumber);
   }

   public SimpleStringProperty descriptionProperty()
   {
      if (description == null)
      {
         description = new SimpleStringProperty();
      }
      return description;
   }

   @Size(max = 256, message = "Inventory_description_Size_validation")
   public String getDescription()
   {
      return descriptionProperty().get();
   }

   public final void setDescription(String description)
   {
      this.descriptionProperty().set(description);
   }

   public SimpleObjectProperty<DocumentProcessingState> inventoryStatusProperty()
   {
      if (inventoryStatus == null)
      {
         inventoryStatus = new SimpleObjectProperty<DocumentProcessingState>();
      }
      return inventoryStatus;
   }

   public DocumentProcessingState getInventoryStatus()
   {
      return inventoryStatusProperty().get();
   }

   public final void setInventoryStatus(DocumentProcessingState inventoryStatus)
   {
      this.inventoryStatusProperty().set(inventoryStatus);
   }

   public SimpleObjectProperty<BigDecimal> gapSaleAmountProperty()
   {
      if (gapSaleAmount == null)
      {
         gapSaleAmount = new SimpleObjectProperty<BigDecimal>();
      }
      return gapSaleAmount;
   }

   public BigDecimal getGapSaleAmount()
   {
      return gapSaleAmountProperty().get();
   }

   public final void setGapSaleAmount(BigDecimal gapSaleAmount)
   {
      this.gapSaleAmountProperty().set(gapSaleAmount);
   }

   public SimpleObjectProperty<BigDecimal> gapPurchaseAmountProperty()
   {
      if (gapPurchaseAmount == null)
      {
         gapPurchaseAmount = new SimpleObjectProperty<BigDecimal>();
      }
      return gapPurchaseAmount;
   }

   public BigDecimal getGapPurchaseAmount()
   {
      return gapPurchaseAmountProperty().get();
   }

   public final void setGapPurchaseAmount(BigDecimal gapPurchaseAmount)
   {
      this.gapPurchaseAmountProperty().set(gapPurchaseAmount);
   }

   public SimpleObjectProperty<Calendar> inventoryDateProperty()
   {
      if (inventoryDate == null)
      {
         inventoryDate = new SimpleObjectProperty<Calendar>();
      }
      return inventoryDate;
   }

   public Calendar getInventoryDate()
   {
      return inventoryDateProperty().get();
   }

   public final void setInventoryDate(Calendar inventoryDate)
   {
      this.inventoryDateProperty().set(inventoryDate);
   }

   public SimpleObjectProperty<ObservableList<InventoryItem>> inventoryItemsProperty()
   {
      if (inventoryItems == null)
      {
         ObservableList<InventoryItem> observableArrayList = FXCollections.observableArrayList();
         inventoryItems = new SimpleObjectProperty<ObservableList<InventoryItem>>(observableArrayList);
      }
      return inventoryItems;
   }

   public List<InventoryItem> getInventoryItems()
   {
      return new ArrayList<InventoryItem>(inventoryItemsProperty().get());
   }

   public final void setInventoryItems(List<InventoryItem> inventoryItems)
   {
      this.inventoryItemsProperty().get().clear();
      if (inventoryItems != null)
         this.inventoryItemsProperty().get().addAll(inventoryItems);
   }

   public final void addToInventoryItems(InventoryItem entity)
   {
      this.inventoryItemsProperty().get().add(entity);
   }

   public SimpleObjectProperty<InventoryRecordingUser> recordingUserProperty()
   {
      if (recordingUser == null)
      {
         recordingUser = new SimpleObjectProperty<InventoryRecordingUser>(new InventoryRecordingUser());
      }
      return recordingUser;
   }

   @NotNull(message = "Inventory_recordingUser_NotNull_validation")
   public InventoryRecordingUser getRecordingUser()
   {
      return recordingUserProperty().get();
   }

   public final void setRecordingUser(InventoryRecordingUser recordingUser)
   {
      if (recordingUser == null)
      {
         recordingUser = new InventoryRecordingUser();
      }
      PropertyReader.copy(recordingUser, getRecordingUser());
      recordingUserProperty().setValue(ObjectUtils.clone(getRecordingUser()));
   }

   public SimpleObjectProperty<InventoryAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<InventoryAgency>(new InventoryAgency());
      }
      return agency;
   }

   @NotNull(message = "Inventory_agency_NotNull_validation")
   public InventoryAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(InventoryAgency agency)
   {
      if (agency == null)
      {
         agency = new InventoryAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
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
      Inventory other = (Inventory) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "inventoryNumber");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
      ObservableList<InventoryItem> f = inventoryItems.get();
      for (InventoryItem e : f)
      {
         e.setId(null);
         e.setVersion(0);
      }
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      Inventory e = new Inventory();
      e.id = id;
      e.version = version;

      e.inventoryNumber = inventoryNumber;
      e.description = description;
      e.inventoryStatus = inventoryStatus;
      e.gapSaleAmount = gapSaleAmount;
      e.gapPurchaseAmount = gapPurchaseAmount;
      e.inventoryDate = inventoryDate;
      e.inventoryItems = inventoryItems;
      e.recordingUser = recordingUser;
      e.agency = agency;
      return e;
   }
}