package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.inventory.Inventory;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Inventory_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryItemInventory implements Association<InventoryItem, Inventory>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty inventoryNumber;
   private SimpleObjectProperty<DocumentProcessingState> inventoryStatus;
   private SimpleObjectProperty<BigDecimal> gapSaleAmount;
   private SimpleObjectProperty<BigDecimal> gapPurchaseAmount;

   public InventoryItemInventory()
   {
   }

   public InventoryItemInventory(Inventory entity)
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
   //		InventoryItemInventory other = (InventoryItemInventory) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "inventoryNumber");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      InventoryItemInventory a = new InventoryItemInventory();
      a.id = id;
      a.version = version;

      a.inventoryNumber = inventoryNumber;
      a.inventoryStatus = inventoryStatus;
      a.gapSaleAmount = gapSaleAmount;
      a.gapPurchaseAmount = gapPurchaseAmount;
      return a;
   }

}
