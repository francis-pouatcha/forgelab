package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementType;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminal;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javaext.format.DateFormatPattern;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("StockMovement_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "movedQty", "movementType", "movementOrigin",
      "movementDestination", "article.articleName" })
@ListField({ "movedQty", "movementType", "movementOrigin",
      "movementDestination", "article.articleName", "agency.name",
      "initialQty", "finalQty", "totalPurchasingPrice", "totalDiscount",
      "totalSalesPrice" })
public class StockMovement
{

   private Long id;
   private int version;

   @Description("StockMovement_originatedDocNumber_description")
   private SimpleStringProperty originatedDocNumber;
   @Description("StockMovement_movementType_description")
   private SimpleObjectProperty<StockMovementType> movementType;
   @Description("StockMovement_movementOrigin_description")
   private SimpleObjectProperty<StockMovementTerminal> movementOrigin;
   @Description("StockMovement_movementDestination_description")
   private SimpleObjectProperty<StockMovementTerminal> movementDestination;
   @Description("StockMovement_movedQty_description")
   private SimpleObjectProperty<BigDecimal> movedQty;
   @Description("StockMovement_initialQty_description")
   private SimpleObjectProperty<BigDecimal> initialQty;
   @Description("StockMovement_finalQty_description")
   private SimpleObjectProperty<BigDecimal> finalQty;
   @Description("StockMovement_totalPurchasingPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalPurchasingPrice;
   @Description("StockMovement_totalDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalDiscount;
   @Description("StockMovement_totalSalesPrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalSalesPrice;
   @Description("StockMovement_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> creationDate;
   @Description("StockMovement_creatingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<StockMovementCreatingUser> creatingUser;
   @Description("StockMovement_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<StockMovementArticle> article;
   @Description("StockMovement_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<StockMovementAgency> agency;

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

   public SimpleStringProperty originatedDocNumberProperty()
   {
      if (originatedDocNumber == null)
      {
         originatedDocNumber = new SimpleStringProperty();
      }
      return originatedDocNumber;
   }

   public String getOriginatedDocNumber()
   {
      return originatedDocNumberProperty().get();
   }

   public final void setOriginatedDocNumber(String originatedDocNumber)
   {
      this.originatedDocNumberProperty().set(originatedDocNumber);
   }

   public SimpleObjectProperty<StockMovementType> movementTypeProperty()
   {
      if (movementType == null)
      {
         movementType = new SimpleObjectProperty<StockMovementType>();
      }
      return movementType;
   }

   @NotNull(message = "StockMovement_movementType_NotNull_validation")
   public StockMovementType getMovementType()
   {
      return movementTypeProperty().get();
   }

   public final void setMovementType(StockMovementType movementType)
   {
      this.movementTypeProperty().set(movementType);
   }

   public SimpleObjectProperty<StockMovementTerminal> movementOriginProperty()
   {
      if (movementOrigin == null)
      {
         movementOrigin = new SimpleObjectProperty<StockMovementTerminal>();
      }
      return movementOrigin;
   }

   public StockMovementTerminal getMovementOrigin()
   {
      return movementOriginProperty().get();
   }

   public final void setMovementOrigin(StockMovementTerminal movementOrigin)
   {
      this.movementOriginProperty().set(movementOrigin);
   }

   public SimpleObjectProperty<StockMovementTerminal> movementDestinationProperty()
   {
      if (movementDestination == null)
      {
         movementDestination = new SimpleObjectProperty<StockMovementTerminal>();
      }
      return movementDestination;
   }

   public StockMovementTerminal getMovementDestination()
   {
      return movementDestinationProperty().get();
   }

   public final void setMovementDestination(StockMovementTerminal movementDestination)
   {
      this.movementDestinationProperty().set(movementDestination);
   }

   public SimpleObjectProperty<BigDecimal> movedQtyProperty()
   {
      if (movedQty == null)
      {
         movedQty = new SimpleObjectProperty<BigDecimal>();
      }
      return movedQty;
   }

   public BigDecimal getMovedQty()
   {
      return movedQtyProperty().get();
   }

   public final void setMovedQty(BigDecimal movedQty)
   {
      this.movedQtyProperty().set(movedQty);
   }

   public SimpleObjectProperty<BigDecimal> initialQtyProperty()
   {
      if (initialQty == null)
      {
         initialQty = new SimpleObjectProperty<BigDecimal>();
      }
      return initialQty;
   }

   public BigDecimal getInitialQty()
   {
      return initialQtyProperty().get();
   }

   public final void setInitialQty(BigDecimal initialQty)
   {
      this.initialQtyProperty().set(initialQty);
   }

   public SimpleObjectProperty<BigDecimal> finalQtyProperty()
   {
      if (finalQty == null)
      {
         finalQty = new SimpleObjectProperty<BigDecimal>();
      }
      return finalQty;
   }

   public BigDecimal getFinalQty()
   {
      return finalQtyProperty().get();
   }

   public final void setFinalQty(BigDecimal finalQty)
   {
      this.finalQtyProperty().set(finalQty);
   }

   public SimpleObjectProperty<BigDecimal> totalPurchasingPriceProperty()
   {
      if (totalPurchasingPrice == null)
      {
         totalPurchasingPrice = new SimpleObjectProperty<BigDecimal>();
      }
      return totalPurchasingPrice;
   }

   @NotNull(message = "StockMovement_totalPurchasingPrice_NotNull_validation")
   public BigDecimal getTotalPurchasingPrice()
   {
      return totalPurchasingPriceProperty().get();
   }

   public final void setTotalPurchasingPrice(BigDecimal totalPurchasingPrice)
   {
      this.totalPurchasingPriceProperty().set(totalPurchasingPrice);
   }

   public SimpleObjectProperty<BigDecimal> totalDiscountProperty()
   {
      if (totalDiscount == null)
      {
         totalDiscount = new SimpleObjectProperty<BigDecimal>();
      }
      return totalDiscount;
   }

   public BigDecimal getTotalDiscount()
   {
      return totalDiscountProperty().get();
   }

   public final void setTotalDiscount(BigDecimal totalDiscount)
   {
      this.totalDiscountProperty().set(totalDiscount);
   }

   public SimpleObjectProperty<BigDecimal> totalSalesPriceProperty()
   {
      if (totalSalesPrice == null)
      {
         totalSalesPrice = new SimpleObjectProperty<BigDecimal>();
      }
      return totalSalesPrice;
   }

   public BigDecimal getTotalSalesPrice()
   {
      return totalSalesPriceProperty().get();
   }

   public final void setTotalSalesPrice(BigDecimal totalSalesPrice)
   {
      this.totalSalesPriceProperty().set(totalSalesPrice);
   }

   public SimpleObjectProperty<Calendar> creationDateProperty()
   {
      if (creationDate == null)
      {
         creationDate = new SimpleObjectProperty<Calendar>();
      }
      return creationDate;
   }

   public Calendar getCreationDate()
   {
      return creationDateProperty().get();
   }

   public final void setCreationDate(Calendar creationDate)
   {
      this.creationDateProperty().set(creationDate);
   }

   public SimpleObjectProperty<StockMovementCreatingUser> creatingUserProperty()
   {
      if (creatingUser == null)
      {
         creatingUser = new SimpleObjectProperty<StockMovementCreatingUser>(new StockMovementCreatingUser());
      }
      return creatingUser;
   }

   @NotNull(message = "StockMovement_creatingUser_NotNull_validation")
   public StockMovementCreatingUser getCreatingUser()
   {
      return creatingUserProperty().get();
   }

   public final void setCreatingUser(StockMovementCreatingUser creatingUser)
   {
      if (creatingUser == null)
      {
         creatingUser = new StockMovementCreatingUser();
      }
      PropertyReader.copy(creatingUser, getCreatingUser());
   }

   public SimpleObjectProperty<StockMovementArticle> articleProperty()
   {
      if (article == null)
      {
         article = new SimpleObjectProperty<StockMovementArticle>(new StockMovementArticle());
      }
      return article;
   }

   @NotNull(message = "StockMovement_article_NotNull_validation")
   public StockMovementArticle getArticle()
   {
      return articleProperty().get();
   }

   public final void setArticle(StockMovementArticle article)
   {
      if (article == null)
      {
         article = new StockMovementArticle();
      }
      PropertyReader.copy(article, getArticle());
   }

   public SimpleObjectProperty<StockMovementAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<StockMovementAgency>(new StockMovementAgency());
      }
      return agency;
   }

   @NotNull(message = "StockMovement_agency_NotNull_validation")
   public StockMovementAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(StockMovementAgency agency)
   {
      if (agency == null)
      {
         agency = new StockMovementAgency();
      }
      PropertyReader.copy(agency, getAgency());
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
      StockMovement other = (StockMovement) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "movedQty", "movementType", "movementOrigin", "movementDestination", "articleName");
   }
}