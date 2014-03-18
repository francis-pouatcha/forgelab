package org.adorsys.adpharma.client.jpa.articlelot;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import java.util.Calendar;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import javax.validation.constraints.Size;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ArticleLot_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "internalPic", "mainPic", "secondaryPic", "articleName",
      "article.articleName", "expirationDate", "stockQuantity",
      "salesPricePU", "purchasePricePU", "totalPurchasePrice",
      "totalSalePrice" })
@ToStringField({ "articleName", "article.articleName" })
public class ArticleLot
{

   private Long id;
   private int version;

   @Description("ArticleLot_internalPic_description")
   private SimpleStringProperty internalPic;
   @Description("ArticleLot_mainPic_description")
   private SimpleStringProperty mainPic;
   @Description("ArticleLot_secondaryPic_description")
   private SimpleStringProperty secondaryPic;
   @Description("ArticleLot_articleName_description")
   private SimpleStringProperty articleName;
   @Description("ArticleLot_stockQuantity_description")
   private SimpleObjectProperty<BigDecimal> stockQuantity;
   @Description("ArticleLot_salesPricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> salesPricePU;
   @Description("ArticleLot_purchasePricePU_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> purchasePricePU;
   @Description("ArticleLot_totalPurchasePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalPurchasePrice;
   @Description("ArticleLot_totalSalePrice_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalSalePrice;
   @Description("ArticleLot_expirationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> expirationDate;
   @Description("ArticleLot_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> creationDate;
   @Description("ArticleLot_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<ArticleLotAgency> agency;
   @Description("ArticleLot_article_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   private SimpleObjectProperty<ArticleLotArticle> article;

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

   public SimpleStringProperty internalPicProperty()
   {
      if (internalPic == null)
      {
         internalPic = new SimpleStringProperty();
      }
      return internalPic;
   }

   @Size(min = 7, message = "ArticleLot_internalPic_Size_validation")
   @NotNull(message = "ArticleLot_internalPic_NotNull_validation")
   public String getInternalPic()
   {
      return internalPicProperty().get();
   }

   public final void setInternalPic(String internalPic)
   {
      this.internalPicProperty().set(internalPic);
   }

   public SimpleStringProperty mainPicProperty()
   {
      if (mainPic == null)
      {
         mainPic = new SimpleStringProperty();
      }
      return mainPic;
   }

   @Size(min = 7, message = "ArticleLot_mainPic_Size_validation")
   public String getMainPic()
   {
      return mainPicProperty().get();
   }

   public final void setMainPic(String mainPic)
   {
      this.mainPicProperty().set(mainPic);
   }

   public SimpleStringProperty secondaryPicProperty()
   {
      if (secondaryPic == null)
      {
         secondaryPic = new SimpleStringProperty();
      }
      return secondaryPic;
   }

   @Size(min = 7, message = "ArticleLot_secondaryPic_Size_validation")
   public String getSecondaryPic()
   {
      return secondaryPicProperty().get();
   }

   public final void setSecondaryPic(String secondaryPic)
   {
      this.secondaryPicProperty().set(secondaryPic);
   }

   public SimpleStringProperty articleNameProperty()
   {
      if (articleName == null)
      {
         articleName = new SimpleStringProperty();
      }
      return articleName;
   }

   @NotNull(message = "ArticleLot_articleName_NotNull_validation")
   public String getArticleName()
   {
      return articleNameProperty().get();
   }

   public final void setArticleName(String articleName)
   {
      this.articleNameProperty().set(articleName);
   }

   public SimpleObjectProperty<BigDecimal> stockQuantityProperty()
   {
      if (stockQuantity == null)
      {
         stockQuantity = new SimpleObjectProperty<BigDecimal>();
      }
      return stockQuantity;
   }

   public BigDecimal getStockQuantity()
   {
      return stockQuantityProperty().get();
   }

   public final void setStockQuantity(BigDecimal stockQuantity)
   {
      this.stockQuantityProperty().set(stockQuantity);
   }

   public SimpleObjectProperty<BigDecimal> salesPricePUProperty()
   {
      if (salesPricePU == null)
      {
         salesPricePU = new SimpleObjectProperty<BigDecimal>();
      }
      return salesPricePU;
   }

   public BigDecimal getSalesPricePU()
   {
      return salesPricePUProperty().get();
   }

   public final void setSalesPricePU(BigDecimal salesPricePU)
   {
      this.salesPricePUProperty().set(salesPricePU);
   }

   public SimpleObjectProperty<BigDecimal> purchasePricePUProperty()
   {
      if (purchasePricePU == null)
      {
         purchasePricePU = new SimpleObjectProperty<BigDecimal>();
      }
      return purchasePricePU;
   }

   public BigDecimal getPurchasePricePU()
   {
      return purchasePricePUProperty().get();
   }

   public final void setPurchasePricePU(BigDecimal purchasePricePU)
   {
      this.purchasePricePUProperty().set(purchasePricePU);
   }

   public SimpleObjectProperty<BigDecimal> totalPurchasePriceProperty()
   {
      if (totalPurchasePrice == null)
      {
         totalPurchasePrice = new SimpleObjectProperty<BigDecimal>();
      }
      return totalPurchasePrice;
   }

   public BigDecimal getTotalPurchasePrice()
   {
      return totalPurchasePriceProperty().get();
   }

   public final void setTotalPurchasePrice(BigDecimal totalPurchasePrice)
   {
      this.totalPurchasePriceProperty().set(totalPurchasePrice);
   }

   public SimpleObjectProperty<BigDecimal> totalSalePriceProperty()
   {
      if (totalSalePrice == null)
      {
         totalSalePrice = new SimpleObjectProperty<BigDecimal>();
      }
      return totalSalePrice;
   }

   public BigDecimal getTotalSalePrice()
   {
      return totalSalePriceProperty().get();
   }

   public final void setTotalSalePrice(BigDecimal totalSalePrice)
   {
      this.totalSalePriceProperty().set(totalSalePrice);
   }

   public SimpleObjectProperty<Calendar> expirationDateProperty()
   {
      if (expirationDate == null)
      {
         expirationDate = new SimpleObjectProperty<Calendar>();
      }
      return expirationDate;
   }

   public Calendar getExpirationDate()
   {
      return expirationDateProperty().get();
   }

   public final void setExpirationDate(Calendar expirationDate)
   {
      this.expirationDateProperty().set(expirationDate);
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

   public SimpleObjectProperty<ArticleLotAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<ArticleLotAgency>(new ArticleLotAgency());
      }
      return agency;
   }

   @NotNull(message = "ArticleLot_agency_NotNull_validation")
   public ArticleLotAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(ArticleLotAgency agency)
   {
      if (agency == null)
      {
         agency = new ArticleLotAgency();
      }
      PropertyReader.copy(agency, getAgency());
   }

   public SimpleObjectProperty<ArticleLotArticle> articleProperty()
   {
      if (article == null)
      {
         article = new SimpleObjectProperty<ArticleLotArticle>(new ArticleLotArticle());
      }
      return article;
   }

   @NotNull(message = "ArticleLot_article_NotNull_validation")
   public ArticleLotArticle getArticle()
   {
      return articleProperty().get();
   }

   public final void setArticle(ArticleLotArticle article)
   {
      if (article == null)
      {
         article = new ArticleLotArticle();
      }
      PropertyReader.copy(article, getArticle());
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
      ArticleLot other = (ArticleLot) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "articleName", "articleName");
   }
}