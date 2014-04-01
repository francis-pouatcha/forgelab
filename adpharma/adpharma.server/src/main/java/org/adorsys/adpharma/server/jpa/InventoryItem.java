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
import java.math.BigDecimal;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.Login;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Inventory;

@Entity
@Description("InventoryItem_description")
@ToStringField({ "expectedQty", "asseccedQty", "gap", "internalPic", "article.articleName" })
@ListField({ "expectedQty", "asseccedQty", "gap", "gapSalesPricePU", "gapPurchasePricePU",
      "gapTotalSalePrice", "gapTotalPurchasePrice", "internalPic",
      "article.articleName" })
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
   @Description("InventoryItem_expectedQty_description")
   private BigDecimal expectedQty;

   @Column
   @Description("InventoryItem_asseccedQty_description")
   private BigDecimal asseccedQty;

   @Column
   @Description("InventoryItem_gap_description")
   private Long gap;

   @Column
   @Description("InventoryItem_gapSalesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal gapSalesPricePU;

   @Column
   @Description("InventoryItem_gapPurchasePricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal gapPurchasePricePU;

   @Column
   @Description("InventoryItem_gapTotalSalePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal gapTotalSalePrice;

   @Column
   @Description("InventoryItem_gapTotalPurchasePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal gapTotalPurchasePrice;

   @ManyToOne
   @Description("InventoryItem_recordingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   @NotNull(message = "InventoryItem_recordingUser_NotNull_validation")
   private Login recordingUser;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("InventoryItem_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date recordingDate;

   @Column
   @Description("InventoryItem_internalPic_description")
   private String internalPic;

   @ManyToOne
   @Description("InventoryItem_article_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   @NotNull(message = "InventoryItem_article_NotNull_validation")
   private Article article;

   @ManyToOne
   @Description("InventoryItem_inventory_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = Inventory.class)
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

   public BigDecimal getExpectedQty()
   {
      return this.expectedQty;
   }

   public void setExpectedQty(final BigDecimal expectedQty)
   {
      this.expectedQty = expectedQty;
   }

   public BigDecimal getAsseccedQty()
   {
      return this.asseccedQty;
   }

   public void setAsseccedQty(final BigDecimal asseccedQty)
   {
      this.asseccedQty = asseccedQty;
   }

   public Long getGap()
   {
      return this.gap;
   }

   public void setGap(final Long gap)
   {
      this.gap = gap;
   }

   public BigDecimal getGapSalesPricePU()
   {
      return this.gapSalesPricePU;
   }

   public void setGapSalesPricePU(final BigDecimal gapSalesPricePU)
   {
      this.gapSalesPricePU = gapSalesPricePU;
   }

   public BigDecimal getGapPurchasePricePU()
   {
      return this.gapPurchasePricePU;
   }

   public void setGapPurchasePricePU(final BigDecimal gapPurchasePricePU)
   {
      this.gapPurchasePricePU = gapPurchasePricePU;
   }

   public BigDecimal getGapTotalSalePrice()
   {
      return this.gapTotalSalePrice;
   }

   public void setGapTotalSalePrice(final BigDecimal gapTotalSalePrice)
   {
      this.gapTotalSalePrice = gapTotalSalePrice;
   }

   public BigDecimal getGapTotalPurchasePrice()
   {
      return this.gapTotalPurchasePrice;
   }

   public void setGapTotalPurchasePrice(final BigDecimal gapTotalPurchasePrice)
   {
      this.gapTotalPurchasePrice = gapTotalPurchasePrice;
   }

   public Login getRecordingUser()
   {
      return this.recordingUser;
   }

   public void setRecordingUser(final Login recordingUser)
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

   public String getInternalPic()
   {
      return this.internalPic;
   }

   public void setInternalPic(final String internalPic)
   {
      this.internalPic = internalPic;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (gap != null)
         result += "gap: " + gap;
      if (internalPic != null && !internalPic.trim().isEmpty())
         result += ", internalPic: " + internalPic;
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