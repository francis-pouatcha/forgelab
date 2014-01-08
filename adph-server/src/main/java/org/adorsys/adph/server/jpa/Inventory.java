package org.adorsys.adph.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import org.adorsys.adph.server.jpa.PharmaUser;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.DocumentProcessingState;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.adph.server.jpa.Site;
import javax.validation.constraints.NotNull;
import org.adorsys.adph.server.jpa.InventoryItem;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.Inventory.description")
@Audited
@XmlRootElement
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
   @Description("org.adorsys.adph.server.jpa.Inventory.inventoryNumber.description")
   private String inventoryNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.Inventory.purchaseOrderNumber.description")
   private String purchaseOrderNumber;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Inventory.recordingUser.description")
   private PharmaUser recordingUser;

   @Column
   @Description("org.adorsys.adph.server.jpa.Inventory.amount.description")
   private BigDecimal amount;

   @Column
   @Description("org.adorsys.adph.server.jpa.Inventory.inventoryStatus.description")
   @Enumerated
   private DocumentProcessingState inventoryStatus;

   @Column
   @Description("org.adorsys.adph.server.jpa.Inventory.note.description")
   @Size(max = 256)
   private String note;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.Inventory.inventoryDate.description")
   private Date inventoryDate;

   @ManyToOne
   @NotNull
   @Description("org.adorsys.adph.server.jpa.Inventory.site.description")
   private Site site;

   @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   @Description("org.adorsys.adph.server.jpa.Inventory.inventoryItems.description")
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

   public String getPurchaseOrderNumber()
   {
      return this.purchaseOrderNumber;
   }

   public void setPurchaseOrderNumber(final String purchaseOrderNumber)
   {
      this.purchaseOrderNumber = purchaseOrderNumber;
   }

   public PharmaUser getRecordingUser()
   {
      return this.recordingUser;
   }

   public void setRecordingUser(final PharmaUser recordingUser)
   {
      this.recordingUser = recordingUser;
   }

   public BigDecimal getAmount()
   {
      return this.amount;
   }

   public void setAmount(final BigDecimal amount)
   {
      this.amount = amount;
   }

   public DocumentProcessingState getInventoryStatus()
   {
      return this.inventoryStatus;
   }

   public void setInventoryStatus(final DocumentProcessingState inventoryStatus)
   {
      this.inventoryStatus = inventoryStatus;
   }

   public String getNote()
   {
      return this.note;
   }

   public void setNote(final String note)
   {
      this.note = note;
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
      if (purchaseOrderNumber != null && !purchaseOrderNumber.trim().isEmpty())
         result += ", purchaseOrderNumber: " + purchaseOrderNumber;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      return result;
   }

   public Site getSite()
   {
      return this.site;
   }

   public void setSite(final Site site)
   {
      this.site = site;
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