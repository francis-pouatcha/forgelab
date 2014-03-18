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
import javax.validation.constraints.Size;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;
import javax.validation.constraints.NotNull;
import org.adorsys.adpharma.server.jpa.Article;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import java.math.BigDecimal;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.ProcurementOrder;

@Entity
@Description("ProcurementOrderItem_description")
@ListField({ "mainPic", "secondaryPic", "articleName", "article.articleName",
      "expirationDate", "qtyOrdered", "freeQuantity", "stockQuantity",
      "salesPricePU", "purchasePricePU", "totalPurchasePrice", "valid" })
@ToStringField({ "articleName", "article.articleName", "qtyOrdered" })
public class ProcurementOrderItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("ProcurementOrderItem_mainPic_description")
   @Size(min = 7, message = "ProcurementOrderItem_mainPic_Size_validation")
   private String mainPic;

   @Column
   @Description("ProcurementOrderItem_secondaryPic_description")
   @Size(min = 7, message = "ProcurementOrderItem_secondaryPic_Size_validation")
   private String secondaryPic;

   @Column
   @Description("ProcurementOrderItem_articleName_description")
   @NotNull(message = "ProcurementOrderItem_articleName_NotNull_validation")
   private String articleName;

   @ManyToOne
   @Description("ProcurementOrderItem_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   @NotNull(message = "ProcurementOrderItem_article_NotNull_validation")
   private Article article;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("ProcurementOrderItem_expirationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private Date expirationDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("ProcurementOrderItem_productRecCreated_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm ")
   private Date productRecCreated;

   @Column
   @Description("ProcurementOrderItem_qtyOrdered_description")
   private BigDecimal qtyOrdered;

   @Column
   @Description("ProcurementOrderItem_availableQty_description")
   private BigDecimal availableQty;

   @Column
   @Description("ProcurementOrderItem_freeQuantity_description")
   private BigDecimal freeQuantity;

   @ManyToOne
   @Description("ProcurementOrderItem_creatingUser_description")
   @NotNull(message = "ProcurementOrderItem_creatingUser_NotNull_validation")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private Login creatingUser;

   @Column
   @Description("ProcurementOrderItem_stockQuantity_description")
   private BigDecimal stockQuantity;

   @Column
   @Description("ProcurementOrderItem_salesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal salesPricePU;

   @Column
   @Description("ProcurementOrderItem_purchasePricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal purchasePricePU;

   @Column
   @Description("ProcurementOrderItem_totalPurchasePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalPurchasePrice;

   @Column
   @Description("ProcurementOrderItem_valid_description")
   private Boolean valid;

   @ManyToOne
   @Description("ProcurementOrderItem_procurementOrder_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = ProcurementOrder.class)
   private ProcurementOrder procurementOrder;

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
         return id.equals(((ProcurementOrderItem) that).id);
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

   public String getMainPic()
   {
      return this.mainPic;
   }

   public void setMainPic(final String mainPic)
   {
      this.mainPic = mainPic;
   }

   public String getSecondaryPic()
   {
      return this.secondaryPic;
   }

   public void setSecondaryPic(final String secondaryPic)
   {
      this.secondaryPic = secondaryPic;
   }

   public String getArticleName()
   {
      return this.articleName;
   }

   public void setArticleName(final String articleName)
   {
      this.articleName = articleName;
   }

   public Article getArticle()
   {
      return this.article;
   }

   public void setArticle(final Article article)
   {
      this.article = article;
   }

   public Date getExpirationDate()
   {
      return this.expirationDate;
   }

   public void setExpirationDate(final Date expirationDate)
   {
      this.expirationDate = expirationDate;
   }

   public Date getProductRecCreated()
   {
      return this.productRecCreated;
   }

   public void setProductRecCreated(final Date productRecCreated)
   {
      this.productRecCreated = productRecCreated;
   }

   public BigDecimal getQtyOrdered()
   {
      return this.qtyOrdered;
   }

   public void setQtyOrdered(final BigDecimal qtyOrdered)
   {
      this.qtyOrdered = qtyOrdered;
   }

   public BigDecimal getAvailableQty()
   {
      return this.availableQty;
   }

   public void setAvailableQty(final BigDecimal availableQty)
   {
      this.availableQty = availableQty;
   }

   public BigDecimal getFreeQuantity()
   {
      return this.freeQuantity;
   }

   public void setFreeQuantity(final BigDecimal freeQuantity)
   {
      this.freeQuantity = freeQuantity;
   }

   public Login getCreatingUser()
   {
      return this.creatingUser;
   }

   public void setCreatingUser(final Login creatingUser)
   {
      this.creatingUser = creatingUser;
   }

   public BigDecimal getStockQuantity()
   {
      return this.stockQuantity;
   }

   public void setStockQuantity(final BigDecimal stockQuantity)
   {
      this.stockQuantity = stockQuantity;
   }

   public BigDecimal getSalesPricePU()
   {
      return this.salesPricePU;
   }

   public void setSalesPricePU(final BigDecimal salesPricePU)
   {
      this.salesPricePU = salesPricePU;
   }

   public BigDecimal getPurchasePricePU()
   {
      return this.purchasePricePU;
   }

   public void setPurchasePricePU(final BigDecimal purchasePricePU)
   {
      this.purchasePricePU = purchasePricePU;
   }

   public BigDecimal getTotalPurchasePrice()
   {
      return this.totalPurchasePrice;
   }

   public void setTotalPurchasePrice(final BigDecimal totalPurchasePrice)
   {
      this.totalPurchasePrice = totalPurchasePrice;
   }

   public Boolean getValid()
   {
      return this.valid;
   }

   public void setValid(final Boolean valid)
   {
      this.valid = valid;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (mainPic != null && !mainPic.trim().isEmpty())
         result += "mainPic: " + mainPic;
      if (secondaryPic != null && !secondaryPic.trim().isEmpty())
         result += ", secondaryPic: " + secondaryPic;
      if (articleName != null && !articleName.trim().isEmpty())
         result += ", articleName: " + articleName;
      if (valid != null)
         result += ", valid: " + valid;
      return result;
   }

   public ProcurementOrder getProcurementOrder()
   {
      return this.procurementOrder;
   }

   public void setProcurementOrder(final ProcurementOrder procurementOrder)
   {
      this.procurementOrder = procurementOrder;
   }
}