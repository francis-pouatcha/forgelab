package org.adorsys.adpharma.client.jpa.procurementorderitem;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import javafx.scene.control.ComboBox;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;

public class ProcurementOrderItemView extends AbstractForm<ProcurementOrderItem>
{

   private TextField mainPic;

   private TextField secondaryPic;

   private TextField articleName;

   private CheckBox valid;

   private ComboBox<DocumentProcessingState> poStatus;

   private BigDecimalField qtyOrdered;

   private BigDecimalField availableQty;

   private BigDecimalField freeQuantity;

   private BigDecimalField stockQuantity;

   private BigDecimalField salesPricePU;

   private BigDecimalField purchasePricePU;

   private BigDecimalField totalPurchasePrice;

   private CalendarTextField expirationDate;

   private CalendarTextField productRecCreated;

   @Inject
   private ProcurementOrderItemProcurementOrderForm procurementOrderItemProcurementOrderForm;

   @Inject
   private ProcurementOrderItemArticleForm procurementOrderItemArticleForm;
   @Inject
   private ProcurementOrderItemArticleSelection procurementOrderItemArticleSelection;

   @Inject
   private ProcurementOrderItemCreatingUserForm procurementOrderItemCreatingUserForm;
   @Inject
   private ProcurementOrderItemCreatingUserSelection procurementOrderItemCreatingUserSelection;

   @Inject
   @Bundle({ CrudKeys.class, ProcurementOrderItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentProcessingState.class)
   private ResourceBundle poStatusBundle;

   @Inject
   private DocumentProcessingStateConverter poStatusConverter;

   @Inject
   private DocumentProcessingStateListCellFatory poStatusListCellFatory;

   @Inject
   private Locale locale;

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;
   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      mainPic = viewBuilder.addTextField("ProcurementOrderItem_mainPic_description.title", "mainPic", resourceBundle);
      secondaryPic = viewBuilder.addTextField("ProcurementOrderItem_secondaryPic_description.title", "secondaryPic", resourceBundle);
      articleName = viewBuilder.addTextField("ProcurementOrderItem_articleName_description.title", "articleName", resourceBundle);
      valid = viewBuilder.addCheckBox("ProcurementOrderItem_valid_description.title", "valid", resourceBundle);
      poStatus = viewBuilder.addComboBox("ProcurementOrderItem_poStatus_description.title", "poStatus", resourceBundle, DocumentProcessingState.values());
      qtyOrdered = viewBuilder.addBigDecimalField("ProcurementOrderItem_qtyOrdered_description.title", "qtyOrdered", resourceBundle, NumberType.INTEGER, locale);
      availableQty = viewBuilder.addBigDecimalField("ProcurementOrderItem_availableQty_description.title", "availableQty", resourceBundle, NumberType.INTEGER, locale);
      freeQuantity = viewBuilder.addBigDecimalField("ProcurementOrderItem_freeQuantity_description.title", "freeQuantity", resourceBundle, NumberType.INTEGER, locale);
      stockQuantity = viewBuilder.addBigDecimalField("ProcurementOrderItem_stockQuantity_description.title", "stockQuantity", resourceBundle, NumberType.INTEGER, locale);
      salesPricePU = viewBuilder.addBigDecimalField("ProcurementOrderItem_salesPricePU_description.title", "salesPricePU", resourceBundle, NumberType.CURRENCY, locale);
      purchasePricePU = viewBuilder.addBigDecimalField("ProcurementOrderItem_purchasePricePU_description.title", "purchasePricePU", resourceBundle, NumberType.CURRENCY, locale);
      totalPurchasePrice = viewBuilder.addBigDecimalField("ProcurementOrderItem_totalPurchasePrice_description.title", "totalPurchasePrice", resourceBundle, NumberType.CURRENCY, locale);
      expirationDate = viewBuilder.addCalendarTextField("ProcurementOrderItem_expirationDate_description.title", "expirationDate", resourceBundle, "dd-MM-yyyy", locale);
      productRecCreated = viewBuilder.addCalendarTextField("ProcurementOrderItem_productRecCreated_description.title", "productRecCreated", resourceBundle, "dd-MM-yyyy HH:mm ", locale);
      viewBuilder.addTitlePane("ProcurementOrderItem_procurementOrder_description.title", resourceBundle);
      viewBuilder.addSubForm("ProcurementOrderItem_procurementOrder_description.title", "procurementOrder", resourceBundle, procurementOrderItemProcurementOrderForm, ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("ProcurementOrderItem_article_description.title", resourceBundle);
      viewBuilder.addSubForm("ProcurementOrderItem_article_description.title", "article", resourceBundle, procurementOrderItemArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProcurementOrderItem_article_description.title", "article", resourceBundle, procurementOrderItemArticleSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("ProcurementOrderItem_creatingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("ProcurementOrderItem_creatingUser_description.title", "creatingUser", resourceBundle, procurementOrderItemCreatingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProcurementOrderItem_creatingUser_description.title", "creatingUser", resourceBundle, procurementOrderItemCreatingUserSelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(poStatus, poStatusConverter, poStatusListCellFatory, poStatusBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      mainPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ProcurementOrderItem>(textInputControlValidator, mainPic, ProcurementOrderItem.class, "mainPic", resourceBundle));
      secondaryPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ProcurementOrderItem>(textInputControlValidator, secondaryPic, ProcurementOrderItem.class, "secondaryPic", resourceBundle));
      articleName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ProcurementOrderItem>(textInputControlValidator, articleName, ProcurementOrderItem.class, "articleName", resourceBundle));
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<ProcurementOrderItem>> validate(ProcurementOrderItem model)
   {
      Set<ConstraintViolation<ProcurementOrderItem>> violations = new HashSet<ConstraintViolation<ProcurementOrderItem>>();
      violations.addAll(textInputControlValidator.validate(mainPic, ProcurementOrderItem.class, "mainPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(secondaryPic, ProcurementOrderItem.class, "secondaryPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(articleName, ProcurementOrderItem.class, "articleName", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(procurementOrderItemArticleSelection.getArticle(), model.getArticle(), ProcurementOrderItem.class, "article", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(procurementOrderItemCreatingUserSelection.getCreatingUser(), model.getCreatingUser(), ProcurementOrderItem.class, "creatingUser", resourceBundle));
      return violations;
   }

   public void bind(ProcurementOrderItem model)
   {
      mainPic.textProperty().bindBidirectional(model.mainPicProperty());
      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      valid.textProperty().bindBidirectional(model.validProperty(), new BooleanStringConverter());
      poStatus.valueProperty().bindBidirectional(model.poStatusProperty());
      qtyOrdered.numberProperty().bindBidirectional(model.qtyOrderedProperty());
      availableQty.numberProperty().bindBidirectional(model.availableQtyProperty());
      freeQuantity.numberProperty().bindBidirectional(model.freeQuantityProperty());
      stockQuantity.numberProperty().bindBidirectional(model.stockQuantityProperty());
      salesPricePU.numberProperty().bindBidirectional(model.salesPricePUProperty());
      purchasePricePU.numberProperty().bindBidirectional(model.purchasePricePUProperty());
      totalPurchasePrice.numberProperty().bindBidirectional(model.totalPurchasePriceProperty());
      expirationDate.calendarProperty().bindBidirectional(model.expirationDateProperty());
      productRecCreated.calendarProperty().bindBidirectional(model.productRecCreatedProperty());
      procurementOrderItemProcurementOrderForm.bind(model);
      procurementOrderItemArticleForm.bind(model);
      procurementOrderItemArticleSelection.bind(model);
      procurementOrderItemCreatingUserForm.bind(model);
      procurementOrderItemCreatingUserSelection.bind(model);
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

   public CheckBox getValid()
   {
      return valid;
   }

   public ComboBox<DocumentProcessingState> getPoStatus()
   {
      return poStatus;
   }

   public BigDecimalField getQtyOrdered()
   {
      return qtyOrdered;
   }

   public BigDecimalField getAvailableQty()
   {
      return availableQty;
   }

   public BigDecimalField getFreeQuantity()
   {
      return freeQuantity;
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

   public CalendarTextField getExpirationDate()
   {
      return expirationDate;
   }

   public CalendarTextField getProductRecCreated()
   {
      return productRecCreated;
   }

   public ProcurementOrderItemProcurementOrderForm getProcurementOrderItemProcurementOrderForm()
   {
      return procurementOrderItemProcurementOrderForm;
   }

   public ProcurementOrderItemArticleForm getProcurementOrderItemArticleForm()
   {
      return procurementOrderItemArticleForm;
   }

   public ProcurementOrderItemArticleSelection getProcurementOrderItemArticleSelection()
   {
      return procurementOrderItemArticleSelection;
   }

   public ProcurementOrderItemCreatingUserForm getProcurementOrderItemCreatingUserForm()
   {
      return procurementOrderItemCreatingUserForm;
   }

   public ProcurementOrderItemCreatingUserSelection getProcurementOrderItemCreatingUserSelection()
   {
      return procurementOrderItemCreatingUserSelection;
   }
}
