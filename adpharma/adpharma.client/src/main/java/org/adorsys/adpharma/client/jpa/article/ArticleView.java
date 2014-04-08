package org.adorsys.adpharma.client.jpa.article;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ArticleView extends AbstractForm<Article>
{

   private TextField articleName;

   private TextField pic;

   private TextField manufacturer;

   private CheckBox active;

   private CheckBox authorizedSale;

   private BigDecimalField maxStockQty;

   private BigDecimalField qtyInStock;

   private BigDecimalField pppu;

   private BigDecimalField sppu;

   private BigDecimalField maxDiscountRate;

   private BigDecimalField totalStockPrice;

   private CalendarTextField lastStockEntry;

   private CalendarTextField lastOutOfStock;

   private CalendarTextField recordingDate;

   @Inject
   private ArticleSectionForm articleSectionForm;
   @Inject
   private ArticleSectionSelection articleSectionSelection;

   @Inject
   private ArticleFamilyForm articleFamilyForm;
   @Inject
   private ArticleFamilySelection articleFamilySelection;

   @Inject
   private ArticleDefaultSalesMarginForm articleDefaultSalesMarginForm;
   @Inject
   private ArticleDefaultSalesMarginSelection articleDefaultSalesMarginSelection;

   @Inject
   private ArticlePackagingModeForm articlePackagingModeForm;
   @Inject
   private ArticlePackagingModeSelection articlePackagingModeSelection;

   @Inject
   private ArticleAgencyForm articleAgencyForm;
   @Inject
   private ArticleAgencySelection articleAgencySelection;

   @Inject
   private ArticleClearanceConfigForm articleClearanceConfigForm;
   @Inject
   private ArticleClearanceConfigSelection articleClearanceConfigSelection;

   @Inject
   @Bundle({ CrudKeys.class, Article.class })
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
      articleName = viewBuilder.addTextField("Article_articleName_description.title", "articleName", resourceBundle);
      pic = viewBuilder.addTextField("Article_pic_description.title", "pic", resourceBundle);
      manufacturer = viewBuilder.addTextField("Article_manufacturer_description.title", "manufacturer", resourceBundle);
      active = viewBuilder.addCheckBox("Article_active_description.title", "active", resourceBundle);
      authorizedSale = viewBuilder.addCheckBox("Article_authorizedSale_description.title", "authorizedSale", resourceBundle);
      maxStockQty = viewBuilder.addBigDecimalField("Article_maxStockQty_description.title", "maxStockQty", resourceBundle, NumberType.INTEGER, locale);
//      qtyInStock = viewBuilder.addBigDecimalField("Article_qtyInStock_description.title", "qtyInStock", resourceBundle, NumberType.INTEGER, locale);
      pppu = viewBuilder.addBigDecimalField("Article_pppu_description.title", "pppu", resourceBundle, NumberType.INTEGER, locale);
      sppu = viewBuilder.addBigDecimalField("Article_sppu_description.title", "sppu", resourceBundle, NumberType.INTEGER, locale);
      maxDiscountRate = viewBuilder.addBigDecimalField("Article_maxDiscountRate_description.title", "maxDiscountRate", resourceBundle, NumberType.PERCENTAGE, locale);
//      totalStockPrice = viewBuilder.addBigDecimalField("Article_totalStockPrice_description.title", "totalStockPrice", resourceBundle, NumberType.CURRENCY, locale);
//      lastStockEntry = viewBuilder.addCalendarTextField("Article_lastStockEntry_description.title", "lastStockEntry", resourceBundle, "dd-MM-yyyy", locale);
//      lastOutOfStock = viewBuilder.addCalendarTextField("Article_lastOutOfStock_description.title", "lastOutOfStock", resourceBundle, "dd-MM-yyyy", locale);
//      recordingDate = viewBuilder.addCalendarTextField("Article_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
//      viewBuilder.addTitlePane("Article_section_description.title", resourceBundle);
//      viewBuilder.addSubForm("Article_section_description.title", "section", resourceBundle, articleSectionForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Article_section_description.title", "section", resourceBundle, articleSectionSelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("Article_family_description.title", resourceBundle);
//      viewBuilder.addSubForm("Article_family_description.title", "family", resourceBundle, articleFamilyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Article_family_description.title", "family", resourceBundle, articleFamilySelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("Article_defaultSalesMargin_description.title", resourceBundle);
//      viewBuilder.addSubForm("Article_defaultSalesMargin_description.title", "defaultSalesMargin", resourceBundle, articleDefaultSalesMarginForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Article_defaultSalesMargin_description.title", "defaultSalesMargin", resourceBundle, articleDefaultSalesMarginSelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("Article_packagingMode_description.title", resourceBundle);
//      viewBuilder.addSubForm("Article_packagingMode_description.title", "packagingMode", resourceBundle, articlePackagingModeForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Article_packagingMode_description.title", "packagingMode", resourceBundle, articlePackagingModeSelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("Article_agency_description.title", resourceBundle);
//      viewBuilder.addSubForm("Article_agency_description.title", "agency", resourceBundle, articleAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Article_agency_description.title", "agency", resourceBundle, articleAgencySelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("Article_clearanceConfig_description.title", resourceBundle);
//      viewBuilder.addSubForm("Article_clearanceConfig_description.title", "clearanceConfig", resourceBundle, articleClearanceConfigForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Article_clearanceConfig_description.title", "clearanceConfig", resourceBundle, articleClearanceConfigSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      articleName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Article>(textInputControlValidator, articleName, Article.class, "articleName", resourceBundle));
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<Article>> validate(Article model)
   {
      Set<ConstraintViolation<Article>> violations = new HashSet<ConstraintViolation<Article>>();
      violations.addAll(textInputControlValidator.validate(articleName, Article.class, "articleName", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(articleSectionSelection.getSection(), model.getSection(), Article.class, "section", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(articleAgencySelection.getAgency(), model.getAgency(), Article.class, "agency", resourceBundle));
      return violations;
   }

   public void bind(Article model)
   {
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      pic.textProperty().bindBidirectional(model.picProperty());
      manufacturer.textProperty().bindBidirectional(model.manufacturerProperty());
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());
      authorizedSale.textProperty().bindBidirectional(model.authorizedSaleProperty(), new BooleanStringConverter());
      maxStockQty.numberProperty().bindBidirectional(model.maxStockQtyProperty());
//      qtyInStock.numberProperty().bindBidirectional(model.qtyInStockProperty());
      pppu.numberProperty().bindBidirectional(model.pppuProperty());
      sppu.numberProperty().bindBidirectional(model.sppuProperty());
      maxDiscountRate.numberProperty().bindBidirectional(model.maxDiscountRateProperty());
//      totalStockPrice.numberProperty() .bind(model.totalStockPriceProperty());
//      totalStockPrice.setDisable(true);
//      lastStockEntry.calendarProperty().bindBidirectional(model.lastStockEntryProperty());
//      lastOutOfStock.calendarProperty().bindBidirectional(model.lastOutOfStockProperty());
//      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
      articleSectionForm.bind(model);
      articleSectionSelection.bind(model);
      articleFamilyForm.bind(model);
      articleFamilySelection.bind(model);
      articleDefaultSalesMarginForm.bind(model);
      articleDefaultSalesMarginSelection.bind(model);
      articlePackagingModeForm.bind(model);
      articlePackagingModeSelection.bind(model);
      articleAgencyForm.bind(model);
      articleAgencySelection.bind(model);
      articleClearanceConfigForm.bind(model);
      articleClearanceConfigSelection.bind(model);
   }

   public TextField getArticleName()
   {
      return articleName;
   }

   public TextField getPic()
   {
      return pic;
   }

   public TextField getManufacturer()
   {
      return manufacturer;
   }

   public CheckBox getActive()
   {
      return active;
   }

   public CheckBox getAuthorizedSale()
   {
      return authorizedSale;
   }

   public BigDecimalField getMaxStockQty()
   {
      return maxStockQty;
   }

   public BigDecimalField getQtyInStock()
   {
      return qtyInStock;
   }

   public BigDecimalField getPppu()
   {
      return pppu;
   }

   public BigDecimalField getSppu()
   {
      return sppu;
   }

   public BigDecimalField getMaxDiscountRate()
   {
      return maxDiscountRate;
   }

   public BigDecimalField getTotalStockPrice()
   {
      return totalStockPrice;
   }

   public CalendarTextField getLastStockEntry()
   {
      return lastStockEntry;
   }

   public CalendarTextField getLastOutOfStock()
   {
      return lastOutOfStock;
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }

   public ArticleSectionForm getArticleSectionForm()
   {
      return articleSectionForm;
   }

   public ArticleSectionSelection getArticleSectionSelection()
   {
      return articleSectionSelection;
   }

   public ArticleFamilyForm getArticleFamilyForm()
   {
      return articleFamilyForm;
   }

   public ArticleFamilySelection getArticleFamilySelection()
   {
      return articleFamilySelection;
   }

   public ArticleDefaultSalesMarginForm getArticleDefaultSalesMarginForm()
   {
      return articleDefaultSalesMarginForm;
   }

   public ArticleDefaultSalesMarginSelection getArticleDefaultSalesMarginSelection()
   {
      return articleDefaultSalesMarginSelection;
   }

   public ArticlePackagingModeForm getArticlePackagingModeForm()
   {
      return articlePackagingModeForm;
   }

   public ArticlePackagingModeSelection getArticlePackagingModeSelection()
   {
      return articlePackagingModeSelection;
   }

   public ArticleAgencyForm getArticleAgencyForm()
   {
      return articleAgencyForm;
   }

   public ArticleAgencySelection getArticleAgencySelection()
   {
      return articleAgencySelection;
   }

   public ArticleClearanceConfigForm getArticleClearanceConfigForm()
   {
      return articleClearanceConfigForm;
   }

   public ArticleClearanceConfigSelection getArticleClearanceConfigSelection()
   {
      return articleClearanceConfigSelection;
   }
}
