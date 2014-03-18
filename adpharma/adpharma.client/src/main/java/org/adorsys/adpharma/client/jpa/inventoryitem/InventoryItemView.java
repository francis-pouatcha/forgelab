package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.inventory.Inventory;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.TextField;
import java.text.NumberFormat;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import jfxtras.scene.control.CalendarTextField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;

public class InventoryItemView extends AbstractForm<InventoryItem>
{

   private TextField gap;

   private TextField internalPic;

   private BigDecimalField expectedQty;

   private BigDecimalField asseccedQty;

   private BigDecimalField gapSalesPricePU;

   private BigDecimalField gapPurchasePricePU;

   private BigDecimalField gapTotalSalePrice;

   private BigDecimalField gapTotalPurchasePrice;

   private CalendarTextField recordingDate;

   @Inject
   private InventoryItemInventoryForm inventoryItemInventoryForm;

   @Inject
   private InventoryItemRecordingUserForm inventoryItemRecordingUserForm;
   @Inject
   private InventoryItemRecordingUserSelection inventoryItemRecordingUserSelection;

   @Inject
   private InventoryItemArticleForm inventoryItemArticleForm;
   @Inject
   private InventoryItemArticleSelection inventoryItemArticleSelection;

   @Inject
   @Bundle({ CrudKeys.class, InventoryItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      gap = viewBuilder.addTextField("InventoryItem_gap_description.title", "gap", resourceBundle);
      internalPic = viewBuilder.addTextField("InventoryItem_internalPic_description.title", "internalPic", resourceBundle);
      expectedQty = viewBuilder.addBigDecimalField("InventoryItem_expectedQty_description.title", "expectedQty", resourceBundle, NumberType.INTEGER, locale);
      asseccedQty = viewBuilder.addBigDecimalField("InventoryItem_asseccedQty_description.title", "asseccedQty", resourceBundle, NumberType.INTEGER, locale);
      gapSalesPricePU = viewBuilder.addBigDecimalField("InventoryItem_gapSalesPricePU_description.title", "gapSalesPricePU", resourceBundle, NumberType.CURRENCY, locale);
      gapPurchasePricePU = viewBuilder.addBigDecimalField("InventoryItem_gapPurchasePricePU_description.title", "gapPurchasePricePU", resourceBundle, NumberType.CURRENCY, locale);
      gapTotalSalePrice = viewBuilder.addBigDecimalField("InventoryItem_gapTotalSalePrice_description.title", "gapTotalSalePrice", resourceBundle, NumberType.CURRENCY, locale);
      gapTotalPurchasePrice = viewBuilder.addBigDecimalField("InventoryItem_gapTotalPurchasePrice_description.title", "gapTotalPurchasePrice", resourceBundle, NumberType.CURRENCY, locale);
      recordingDate = viewBuilder.addCalendarTextField("InventoryItem_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("InventoryItem_inventory_description.title", resourceBundle);
      viewBuilder.addSubForm("InventoryItem_inventory_description.title", "inventory", resourceBundle, inventoryItemInventoryForm, ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("InventoryItem_recordingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("InventoryItem_recordingUser_description.title", "recordingUser", resourceBundle, inventoryItemRecordingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("InventoryItem_recordingUser_description.title", "recordingUser", resourceBundle, inventoryItemRecordingUserSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("InventoryItem_article_description.title", resourceBundle);
      viewBuilder.addSubForm("InventoryItem_article_description.title", "article", resourceBundle, inventoryItemArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("InventoryItem_article_description.title", "article", resourceBundle, inventoryItemArticleSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<InventoryItem>> validate(InventoryItem model)
   {
      Set<ConstraintViolation<InventoryItem>> violations = new HashSet<ConstraintViolation<InventoryItem>>();
      violations.addAll(toOneAggreggationFieldValidator.validate(inventoryItemRecordingUserSelection.getRecordingUser(), model.getRecordingUser(), InventoryItem.class, "recordingUser", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(inventoryItemArticleSelection.getArticle(), model.getArticle(), InventoryItem.class, "article", resourceBundle));
      return violations;
   }

   public void bind(InventoryItem model)
   {
      gap.textProperty().bindBidirectional(model.gapProperty(), NumberFormat.getInstance(locale));
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      expectedQty.numberProperty().bindBidirectional(model.expectedQtyProperty());
      asseccedQty.numberProperty().bindBidirectional(model.asseccedQtyProperty());
      gapSalesPricePU.numberProperty().bindBidirectional(model.gapSalesPricePUProperty());
      gapPurchasePricePU.numberProperty().bindBidirectional(model.gapPurchasePricePUProperty());
      gapTotalSalePrice.numberProperty().bindBidirectional(model.gapTotalSalePriceProperty());
      gapTotalPurchasePrice.numberProperty().bindBidirectional(model.gapTotalPurchasePriceProperty());
      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
      inventoryItemInventoryForm.bind(model);
      inventoryItemRecordingUserForm.bind(model);
      inventoryItemRecordingUserSelection.bind(model);
      inventoryItemArticleForm.bind(model);
      inventoryItemArticleSelection.bind(model);
   }

   public TextField getGap()
   {
      return gap;
   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public BigDecimalField getExpectedQty()
   {
      return expectedQty;
   }

   public BigDecimalField getAsseccedQty()
   {
      return asseccedQty;
   }

   public BigDecimalField getGapSalesPricePU()
   {
      return gapSalesPricePU;
   }

   public BigDecimalField getGapPurchasePricePU()
   {
      return gapPurchasePricePU;
   }

   public BigDecimalField getGapTotalSalePrice()
   {
      return gapTotalSalePrice;
   }

   public BigDecimalField getGapTotalPurchasePrice()
   {
      return gapTotalPurchasePrice;
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }

   public InventoryItemInventoryForm getInventoryItemInventoryForm()
   {
      return inventoryItemInventoryForm;
   }

   public InventoryItemRecordingUserForm getInventoryItemRecordingUserForm()
   {
      return inventoryItemRecordingUserForm;
   }

   public InventoryItemRecordingUserSelection getInventoryItemRecordingUserSelection()
   {
      return inventoryItemRecordingUserSelection;
   }

   public InventoryItemArticleForm getInventoryItemArticleForm()
   {
      return inventoryItemArticleForm;
   }

   public InventoryItemArticleSelection getInventoryItemArticleSelection()
   {
      return inventoryItemArticleSelection;
   }
}
