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
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.adpharma.server.jpa.Login;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.InventoryItem;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Description("Inventory_description")
@ToStringField("inventoryNumber")
@ListField({ "inventoryNumber", "gapSaleAmount", "gapPurchaseAmount", "inventoryStatus",
      "agency.name" })
public class Inventory implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("Inventory_inventoryNumber_description")
   private String inventoryNumber;

   @ManyToOne
   @Description("Inventory_recordingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   @NotNull(message = "Inventory_recordingUser_NotNull_validation")
   private Login recordingUser;

   @Column
   @Description("Inventory_gapSaleAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal gapSaleAmount;

   @Column
   @Description("Inventory_gapPurchaseAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal gapPurchaseAmount;

   @Column
   @Description("Inventory_inventoryStatus_description")
   @Enumerated
   private DocumentProcessingState inventoryStatus;

   @Column
   @Description("Inventory_description_description")
   @Size(max = 256, message = "Inventory_description_Size_validation")
   private String description;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Inventory_inventoryDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date inventoryDate;

   @ManyToOne
   @Description("Inventory_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   @NotNull(message = "Inventory_agency_NotNull_validation")
   private Agency agency;

   @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
   @Description("Inventory_inventoryItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = InventoryItem.class, selectionMode = SelectionMode.TABLE)
   private Set<InventoryItem> inventoryItems = new HashSet<InventoryItem>();

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
         return id.equals(((Inventory) that).id);
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

   public String getInventoryNumber()
   {
      return this.inventoryNumber;
   }

   public void setInventoryNumber(final String inventoryNumber)
   {
      this.inventoryNumber = inventoryNumber;
   }

   public Login getRecordingUser()
   {
      return this.recordingUser;
   }

   public void setRecordingUser(final Login recordingUser)
   {
      this.recordingUser = recordingUser;
   }

   public BigDecimal getGapSaleAmount()
   {
      return this.gapSaleAmount;
   }

   public void setGapSaleAmount(final BigDecimal gapSaleAmount)
   {
      this.gapSaleAmount = gapSaleAmount;
   }

   public BigDecimal getGapPurchaseAmount()
   {
      return this.gapPurchaseAmount;
   }

   public void setGapPurchaseAmount(final BigDecimal gapPurchaseAmount)
   {
      this.gapPurchaseAmount = gapPurchaseAmount;
   }

   public DocumentProcessingState getInventoryStatus()
   {
      return this.inventoryStatus;
   }

   public void setInventoryStatus(final DocumentProcessingState inventoryStatus)
   {
      this.inventoryStatus = inventoryStatus;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public Date getInventoryDate()
   {
      return this.inventoryDate;
   }

   public void setInventoryDate(final Date inventoryDate)
   {
      this.inventoryDate = inventoryDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (inventoryNumber != null && !inventoryNumber.trim().isEmpty())
         result += "inventoryNumber: " + inventoryNumber;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      return result;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
   }

   public Set<InventoryItem> getInventoryItems()
   {
      return this.inventoryItems;
   }

   public void setInventoryItems(final Set<InventoryItem> inventoryItems)
   {
      this.inventoryItems = inventoryItems;
   }
}