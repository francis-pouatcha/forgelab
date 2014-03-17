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
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import javax.validation.constraints.Size;
import org.adorsys.javaext.list.ListField;
import org.adorsys.adpharma.server.jpa.Article;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.ToStringField;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.Delivery;

@Entity
@Description("DeliveryItem_description")
@ListField({ "internalPic", "secondaryPic", "mainPic", "article.articleName",
      "articleName", "initialQty", "finalQty", "totalPurchasingPrice",
      "totalDiscount", "totalSalesPrice" })
@ToStringField({ "article.articleName", "articleName" })
public class DeliveryItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("DeliveryItem_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date creationDate;

   @Column
   @Description("DeliveryItem_internalPic_description")
   @Size(min = 7, message = "DeliveryItem_internalPic_Size_validation")
   private String internalPic;

   @Column
   @Description("DeliveryItem_secondaryPic_description")
   @Size(min = 7, message = "DeliveryItem_secondaryPic_Size_validation")
   private String secondaryPic;

   @Column
   @Description("DeliveryItem_mainPic_description")
   @Size(min = 7, message = "DeliveryItem_mainPic_Size_validation")
   private String mainPic;

   @ManyToOne
   @Description("DeliveryItem_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   @NotNull(message = "DeliveryItem_article_NotNull_validation")
   private Article article;

   @Column
   @Description("DeliveryItem_articleName_description")
   private String articleName;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("DeliveryItem_productRecCreated_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private Date productRecCreated;

   @Column
   @Description("DeliveryItem_initialQty_description")
   private BigDecimal initialQty;

   @Column
   @Description("DeliveryItem_finalQty_description")
   private BigDecimal finalQty;

   @Column
   @Description("DeliveryItem_totalPurchasingPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   @NotNull(message = "DeliveryItem_totalPurchasingPrice_NotNull_validation")
   private BigDecimal totalPurchasingPrice;

   @Column
   @Description("DeliveryItem_totalDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalDiscount;

   @Column
   @Description("DeliveryItem_totalSalesPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalSalesPrice;

   @ManyToOne
   @Description("DeliveryItem_delivery_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = Delivery.class)
   private Delivery delivery;

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
         return id.equals(((DeliveryItem) that).id);
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

   public Date getCreationDate()
   {
      return this.creationDate;
   }

   public void setCreationDate(final Date creationDate)
   {
      this.creationDate = creationDate;
   }

   public String getInternalPic()
   {
      return this.internalPic;
   }

   public void setInternalPic(final String internalPic)
   {
      this.internalPic = internalPic;
   }

   public String getSecondaryPic()
   {
      return this.secondaryPic;
   }

   public void setSecondaryPic(final String secondaryPic)
   {
      this.secondaryPic = secondaryPic;
   }

   public String getMainPic()
   {
      return this.mainPic;
   }

   public void setMainPic(final String mainPic)
   {
      this.mainPic = mainPic;
   }

   public Article getArticle()
   {
      return this.article;
   }

   public void setArticle(final Article article)
   {
      this.article = article;
   }

   public String getArticleName()
   {
      return this.articleName;
   }

   public void setArticleName(final String articleName)
   {
      this.articleName = articleName;
   }

   public Date getProductRecCreated()
   {
      return this.productRecCreated;
   }

   public void setProductRecCreated(final Date productRecCreated)
   {
      this.productRecCreated = productRecCreated;
   }

   public BigDecimal getInitialQty()
   {
      return this.initialQty;
   }

   public void setInitialQty(final BigDecimal initialQty)
   {
      this.initialQty = initialQty;
   }

   public BigDecimal getFinalQty()
   {
      return this.finalQty;
   }

   public void setFinalQty(final BigDecimal finalQty)
   {
      this.finalQty = finalQty;
   }

   public BigDecimal getTotalPurchasingPrice()
   {
      return this.totalPurchasingPrice;
   }

   public void setTotalPurchasingPrice(final BigDecimal totalPurchasingPrice)
   {
      this.totalPurchasingPrice = totalPurchasingPrice;
   }

   public BigDecimal getTotalDiscount()
   {
      return this.totalDiscount;
   }

   public void setTotalDiscount(final BigDecimal totalDiscount)
   {
      this.totalDiscount = totalDiscount;
   }

   public BigDecimal getTotalSalesPrice()
   {
      return this.totalSalesPrice;
   }

   public void setTotalSalesPrice(final BigDecimal totalSalesPrice)
   {
      this.totalSalesPrice = totalSalesPrice;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (internalPic != null && !internalPic.trim().isEmpty())
         result += "internalPic: " + internalPic;
      if (secondaryPic != null && !secondaryPic.trim().isEmpty())
         result += ", secondaryPic: " + secondaryPic;
      if (mainPic != null && !mainPic.trim().isEmpty())
         result += ", mainPic: " + mainPic;
      if (articleName != null && !articleName.trim().isEmpty())
         result += ", articleName: " + articleName;
      return result;
   }

   public Delivery getDelivery()
   {
      return this.delivery;
   }

   public void setDelivery(final Delivery delivery)
   {
      this.delivery = delivery;
   }
}