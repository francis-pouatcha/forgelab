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
import org.adorsys.adpharma.server.jpa.Login;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import java.math.BigDecimal;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.adpharma.server.jpa.StockMovementType;
import javax.persistence.Enumerated;
import org.adorsys.adpharma.server.jpa.StockMovementTerminal;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import javax.validation.constraints.Size;

@Entity
@Description("StockMovement_description")
@ToStringField({ "movedQty", "movementType", "movementOrigin", "movementDestination",
      "article.articleName" })
@ListField({ "movedQty", "movementType", "movementOrigin", "movementDestination",
      "article.articleName", "agency.name", "initialQty", "finalQty",
      "totalPurchasingPrice", "totalDiscount", "totalSalesPrice",
      "internalPic" })
public class StockMovement implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("StockMovement_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date creationDate;

   @ManyToOne
   @Description("StockMovement_creatingUser_description")
   @NotNull(message = "StockMovement_creatingUser_NotNull_validation")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private Login creatingUser;

   @Column
   @Description("StockMovement_movedQty_description")
   private BigDecimal movedQty;

   @Column
   @Description("StockMovement_movementType_description")
   @Enumerated
   @NotNull(message = "StockMovement_movementType_NotNull_validation")
   private StockMovementType movementType;

   @Column
   @Description("StockMovement_movementOrigin_description")
   @Enumerated
   private StockMovementTerminal movementOrigin;

   @Column
   @Description("StockMovement_movementDestination_description")
   @Enumerated
   private StockMovementTerminal movementDestination;

   @ManyToOne
   @Description("StockMovement_article_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   @NotNull(message = "StockMovement_article_NotNull_validation")
   private Article article;

   @Column
   @Description("StockMovement_originatedDocNumber_description")
   private String originatedDocNumber;

   @ManyToOne
   @Description("StockMovement_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   @NotNull(message = "StockMovement_agency_NotNull_validation")
   private Agency agency;

   @Column
   @Description("StockMovement_initialQty_description")
   private BigDecimal initialQty;

   @Column
   @Description("StockMovement_finalQty_description")
   private BigDecimal finalQty;

   @Column
   @Description("StockMovement_totalPurchasingPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   @NotNull(message = "StockMovement_totalPurchasingPrice_NotNull_validation")
   private BigDecimal totalPurchasingPrice;

   @Column
   @Description("StockMovement_totalDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalDiscount;

   @Column
   @Description("StockMovement_totalSalesPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalSalesPrice;

   @Column
   @Description("StockMovement_internalPic_description")
   @Size(min = 1, message = "StockMovement_internalPic_Size_validation")
   @NotNull(message = "StockMovement_internalPic_NotNull_validation")
   private String internalPic;

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
         return id.equals(((StockMovement) that).id);
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

   public Login getCreatingUser()
   {
      return this.creatingUser;
   }

   public void setCreatingUser(final Login creatingUser)
   {
      this.creatingUser = creatingUser;
   }

   public BigDecimal getMovedQty()
   {
      return this.movedQty;
   }

   public void setMovedQty(final BigDecimal movedQty)
   {
      this.movedQty = movedQty;
   }

   public StockMovementType getMovementType()
   {
      return this.movementType;
   }

   public void setMovementType(final StockMovementType movementType)
   {
      this.movementType = movementType;
   }

   public StockMovementTerminal getMovementOrigin()
   {
      return this.movementOrigin;
   }

   public void setMovementOrigin(final StockMovementTerminal movementOrigin)
   {
      this.movementOrigin = movementOrigin;
   }

   public StockMovementTerminal getMovementDestination()
   {
      return this.movementDestination;
   }

   public void setMovementDestination(
         final StockMovementTerminal movementDestination)
   {
      this.movementDestination = movementDestination;
   }

   public Article getArticle()
   {
      return this.article;
   }

   public void setArticle(final Article article)
   {
      this.article = article;
   }

   public String getOriginatedDocNumber()
   {
      return this.originatedDocNumber;
   }

   public void setOriginatedDocNumber(final String originatedDocNumber)
   {
      this.originatedDocNumber = originatedDocNumber;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
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
      if (originatedDocNumber != null && !originatedDocNumber.trim().isEmpty())
         result += "originatedDocNumber: " + originatedDocNumber;
      if (internalPic != null && !internalPic.trim().isEmpty())
         result += ", internalPic: " + internalPic;
      return result;
   }
}