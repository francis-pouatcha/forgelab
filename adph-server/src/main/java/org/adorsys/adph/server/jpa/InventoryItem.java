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
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.PharmaUser;
import javax.persistence.ManyToOne;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.adph.server.jpa.Article;
import org.adorsys.adph.server.jpa.Inventory;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.InventoryItem.description")
@Audited
@XmlRootElement
public class InventoryItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.InventoryItem.iiNumber.description")
   private String iiNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.InventoryItem.expectedQty.description")
   private long expectedQty;

   @Column
   @Description("org.adorsys.adph.server.jpa.InventoryItem.asseccedQty.description")
   private long asseccedQty;

   @Column
   @Description("org.adorsys.adph.server.jpa.InventoryItem.gap.description")
   private long gap;

   @Column
   @Description("org.adorsys.adph.server.jpa.InventoryItem.lastSalesPricePU.description")
   private BigDecimal lastSalesPricePU;

   @Column
   @Description("org.adorsys.adph.server.jpa.InventoryItem.totalPrice.description")
   private BigDecimal totalPrice;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.InventoryItem.recordingUser.description")
   private PharmaUser recordingUser;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.InventoryItem.recordingDate.description")
   private Date recordingDate;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.InventoryItem.article.description")
   private Article article;

   @ManyToOne
   private Inventory inventory;

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
         return id.equals(((InventoryItem) that).id);
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

   public String getIiNumber()
   {
      return this.iiNumber;
   }

   public void setIiNumber(final String iiNumber)
   {
      this.iiNumber = iiNumber;
   }

   public long getExpectedQty()
   {
      return this.expectedQty;
   }

   public void setExpectedQty(final long expectedQty)
   {
      this.expectedQty = expectedQty;
   }

   public long getAsseccedQty()
   {
      return this.asseccedQty;
   }

   public void setAsseccedQty(final long asseccedQty)
   {
      this.asseccedQty = asseccedQty;
   }

   public long getGap()
   {
      return this.gap;
   }

   public void setGap(final long gap)
   {
      this.gap = gap;
   }

   public BigDecimal getLastSalesPricePU()
   {
      return this.lastSalesPricePU;
   }

   public void setLastSalesPricePU(final BigDecimal lastSalesPricePU)
   {
      this.lastSalesPricePU = lastSalesPricePU;
   }

   public BigDecimal getTotalPrice()
   {
      return this.totalPrice;
   }

   public void setTotalPrice(final BigDecimal totalPrice)
   {
      this.totalPrice = totalPrice;
   }

   public PharmaUser getRecordingUser()
   {
      return this.recordingUser;
   }

   public void setRecordingUser(final PharmaUser recordingUser)
   {
      this.recordingUser = recordingUser;
   }

   public Date getRecordingDate()
   {
      return this.recordingDate;
   }

   public void setRecordingDate(final Date recordingDate)
   {
      this.recordingDate = recordingDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (iiNumber != null && !iiNumber.trim().isEmpty())
         result += "iiNumber: " + iiNumber;
      result += ", expectedQty: " + expectedQty;
      result += ", asseccedQty: " + asseccedQty;
      result += ", gap: " + gap;
      return result;
   }

   public Article getArticle()
   {
      return this.article;
   }

   public void setArticle(final Article article)
   {
      this.article = article;
   }

   public Inventory getInventory()
   {
      return this.inventory;
   }

   public void setInventory(final Inventory inventory)
   {
      this.inventory = inventory;
   }
}