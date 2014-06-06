package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import java.util.Calendar;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

public class ArticleLotView extends AbstractForm<ArticleLot>
{

   private TextField internalPic;

   private TextField mainPic;

   private TextField secondaryPic;

   private TextField articleName;

   private BigDecimalField stockQuantity;

   private BigDecimalField salesPricePU;

   private BigDecimalField purchasePricePU;

   private BigDecimalField totalPurchasePrice;

   private BigDecimalField totalSalePrice;

   private CalendarTextField expirationDate;

   private CalendarTextField creationDate;

   @Inject
   private ArticleLotAgencyForm articleLotAgencyForm;
   @Inject
   private ArticleLotAgencySelection articleLotAgencySelection;

   @Inject
   private ArticleLotArticleForm articleLotArticleForm;
   @Inject
   private ArticleLotArticleSelection articleLotArticleSelection;

   @Inject
   private ArticleLotVatForm articleLotVatForm;
   @Inject
   private ArticleLotVatSelection articleLotVatSelection;

   @Inject
   @Bundle({ CrudKeys.class, ArticleLot.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private TextInputControlValidator textInputControlValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("ArticleLot_internalPic_description.title", "internalPic", resourceBundle,ViewModel.READ_ONLY);
      mainPic = viewBuilder.addTextField("ArticleLot_mainPic_description.title", "mainPic", resourceBundle,ViewModel.READ_ONLY);
      secondaryPic = viewBuilder.addTextField("ArticleLot_secondaryPic_description.title", "secondaryPic", resourceBundle,ViewModel.READ_ONLY);
      articleName = viewBuilder.addTextField("ArticleLot_articleName_description.title", "articleName", resourceBundle,ViewModel.READ_ONLY);
      stockQuantity = viewBuilder.addBigDecimalField("ArticleLot_stockQuantity_description.title", "stockQuantity", resourceBundle, NumberType.INTEGER, locale,ViewModel.READ_ONLY);
      salesPricePU = viewBuilder.addBigDecimalField("ArticleLot_salesPricePU_description.title", "salesPricePU", resourceBundle, NumberType.CURRENCY, locale);
      purchasePricePU = viewBuilder.addBigDecimalField("ArticleLot_purchasePricePU_description.title", "purchasePricePU", resourceBundle, NumberType.CURRENCY, locale);
      totalPurchasePrice = viewBuilder.addBigDecimalField("ArticleLot_totalPurchasePrice_description.title", "totalPurchasePrice", resourceBundle, NumberType.CURRENCY, locale);
      totalSalePrice = viewBuilder.addBigDecimalField("ArticleLot_totalSalePrice_description.title", "totalSalePrice", resourceBundle, NumberType.CURRENCY, locale);
      expirationDate = viewBuilder.addCalendarTextField("ArticleLot_expirationDate_description.title", "expirationDate", resourceBundle, "dd-MM-yyyy", locale);
      creationDate = viewBuilder.addCalendarTextField("ArticleLot_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale,ViewModel.READ_ONLY);
//      viewBuilder.addTitlePane("ArticleLot_agency_description.title", resourceBundle);
//      viewBuilder.addSubForm("ArticleLot_agency_description.title", "agency", resourceBundle, articleLotAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ArticleLot_agency_description.title", "agency", resourceBundle, articleLotAgencySelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("ArticleLot_article_description.title", resourceBundle);
      viewBuilder.addSubForm("ArticleLot_article_description.title", "article", resourceBundle, articleLotArticleForm, ViewModel.READ_ONLY);
//      viewBuilder.addSubForm("ArticleLot_article_description.title", "article", resourceBundle, articleLotArticleSelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("ArticleLot_vat_description.title", resourceBundle);
//      viewBuilder.addSubForm("ArticleLot_vat_description.title", "vat", resourceBundle, articleLotVatForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ArticleLot_vat_description.title", "vat", resourceBundle, articleLotVatSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      internalPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLot>(textInputControlValidator, internalPic, ArticleLot.class, "internalPic", resourceBundle));
      mainPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLot>(textInputControlValidator, mainPic, ArticleLot.class, "mainPic", resourceBundle));
      secondaryPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLot>(textInputControlValidator, secondaryPic, ArticleLot.class, "secondaryPic", resourceBundle));
      articleName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLot>(textInputControlValidator, articleName, ArticleLot.class, "articleName", resourceBundle));
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<ArticleLot>> validate(ArticleLot model)
   {
      Set<ConstraintViolation<ArticleLot>> violations = new HashSet<ConstraintViolation<ArticleLot>>();
      violations.addAll(textInputControlValidator.validate(internalPic, ArticleLot.class, "internalPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(mainPic, ArticleLot.class, "mainPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(secondaryPic, ArticleLot.class, "secondaryPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(articleName, ArticleLot.class, "articleName", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(articleLotAgencySelection.getAgency(), model.getAgency(), ArticleLot.class, "agency", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(articleLotArticleSelection.getArticle(), model.getArticle(), ArticleLot.class, "article", resourceBundle));
      return violations;
   }

   public void bind(ArticleLot model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      mainPic.textProperty().bindBidirectional(model.mainPicProperty());
      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      stockQuantity.numberProperty().bindBidirectional(model.stockQuantityProperty());
      salesPricePU.numberProperty().bindBidirectional(model.salesPricePUProperty());
      purchasePricePU.numberProperty().bindBidirectional(model.purchasePricePUProperty());
      totalPurchasePrice.numberProperty().bindBidirectional(model.totalPurchasePriceProperty());
      totalSalePrice.numberProperty().bindBidirectional(model.totalSalePriceProperty());
      expirationDate.calendarProperty().bindBidirectional(model.expirationDateProperty());
      creationDate.calendarProperty().bindBidirectional(model.creationDateProperty());
      articleLotAgencyForm.bind(model);
      articleLotAgencySelection.bind(model);
      articleLotArticleForm.bind(model);
      articleLotArticleSelection.bind(model);
      articleLotVatForm.bind(model);
      articleLotVatSelection.bind(model);
   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public TextField getMainPic()
   {
      return mainPic;
   }

   public TextField getSecondaryPic()
   {
      return secondaryPic;
   }

   public TextField getArticleName()
   {
      return articleName;
   }

   public BigDecimalField getStockQuantity()
   {
      return stockQuantity;
   }

   public BigDecimalField getSalesPricePU()
   {
      return salesPricePU;
   }

   public BigDecimalField getPurchasePricePU()
   {
      return purchasePricePU;
   }

   public BigDecimalField getTotalPurchasePrice()
   {
      return totalPurchasePrice;
   }

   public BigDecimalField getTotalSalePrice()
   {
      return totalSalePrice;
   }

   public CalendarTextField getExpirationDate()
   {
      return expirationDate;
   }

   public CalendarTextField getCreationDate()
   {
      return creationDate;
   }

   public ArticleLotAgencyForm getArticleLotAgencyForm()
   {
      return articleLotAgencyForm;
   }

   public ArticleLotAgencySelection getArticleLotAgencySelection()
   {
      return articleLotAgencySelection;
   }

   public ArticleLotArticleForm getArticleLotArticleForm()
   {
      return articleLotArticleForm;
   }

   public ArticleLotArticleSelection getArticleLotArticleSelection()
   {
      return articleLotArticleSelection;
   }

   public ArticleLotVatForm getArticleLotVatForm()
   {
      return articleLotVatForm;
   }

   public ArticleLotVatSelection getArticleLotVatSelection()
   {
      return articleLotVatSelection;
   }
}
