package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
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
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

public class DeliveryItemView extends AbstractForm<DeliveryItem>
{

   private TextField internalPic;

   private TextField mainPic;

   private TextField secondaryPic;

   private TextField articleName;

   private BigDecimalField qtyOrdered;

   private BigDecimalField availableQty;

   private BigDecimalField freeQuantity;

   private BigDecimalField stockQuantity;

   private BigDecimalField salesPricePU;

   private BigDecimalField purchasePricePU;

   private BigDecimalField totalPurchasePrice;

   private CalendarTextField creationDate;

   private CalendarTextField expirationDate;

   @Inject
   private DeliveryItemDeliveryForm deliveryItemDeliveryForm;

   @Inject
   private DeliveryItemArticleForm deliveryItemArticleForm;
   @Inject
   private DeliveryItemArticleSelection deliveryItemArticleSelection;

   @Inject
   private DeliveryItemCreatingUserForm deliveryItemCreatingUserForm;
   @Inject
   private DeliveryItemCreatingUserSelection deliveryItemCreatingUserSelection;

   @Inject
   @Bundle({ CrudKeys.class, DeliveryItem.class })
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
      internalPic = viewBuilder.addTextField("DeliveryItem_internalPic_description.title", "internalPic", resourceBundle);
      mainPic = viewBuilder.addTextField("DeliveryItem_mainPic_description.title", "mainPic", resourceBundle);
      secondaryPic = viewBuilder.addTextField("DeliveryItem_secondaryPic_description.title", "secondaryPic", resourceBundle);
      articleName = viewBuilder.addTextField("DeliveryItem_articleName_description.title", "articleName", resourceBundle);
      qtyOrdered = viewBuilder.addBigDecimalField("DeliveryItem_qtyOrdered_description.title", "qtyOrdered", resourceBundle, NumberType.INTEGER, locale);
      availableQty = viewBuilder.addBigDecimalField("DeliveryItem_availableQty_description.title", "availableQty", resourceBundle, NumberType.INTEGER, locale);
      freeQuantity = viewBuilder.addBigDecimalField("DeliveryItem_freeQuantity_description.title", "freeQuantity", resourceBundle, NumberType.INTEGER, locale);
      stockQuantity = viewBuilder.addBigDecimalField("DeliveryItem_stockQuantity_description.title", "stockQuantity", resourceBundle, NumberType.INTEGER, locale);
      salesPricePU = viewBuilder.addBigDecimalField("DeliveryItem_salesPricePU_description.title", "salesPricePU", resourceBundle, NumberType.CURRENCY, locale);
      purchasePricePU = viewBuilder.addBigDecimalField("DeliveryItem_purchasePricePU_description.title", "purchasePricePU", resourceBundle, NumberType.CURRENCY, locale);
      totalPurchasePrice = viewBuilder.addBigDecimalField("DeliveryItem_totalPurchasePrice_description.title", "totalPurchasePrice", resourceBundle, NumberType.CURRENCY, locale);
      creationDate = viewBuilder.addCalendarTextField("DeliveryItem_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      expirationDate = viewBuilder.addCalendarTextField("DeliveryItem_expirationDate_description.title", "expirationDate", resourceBundle, "dd-MM-yyyy", locale);
      viewBuilder.addTitlePane("DeliveryItem_delivery_description.title", resourceBundle);
      viewBuilder.addSubForm("DeliveryItem_delivery_description.title", "delivery", resourceBundle, deliveryItemDeliveryForm, ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("DeliveryItem_article_description.title", resourceBundle);
      viewBuilder.addSubForm("DeliveryItem_article_description.title", "article", resourceBundle, deliveryItemArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("DeliveryItem_article_description.title", "article", resourceBundle, deliveryItemArticleSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("DeliveryItem_creatingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("DeliveryItem_creatingUser_description.title", "creatingUser", resourceBundle, deliveryItemCreatingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("DeliveryItem_creatingUser_description.title", "creatingUser", resourceBundle, deliveryItemCreatingUserSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      internalPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<DeliveryItem>(textInputControlValidator, internalPic, DeliveryItem.class, "internalPic", resourceBundle));
      mainPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<DeliveryItem>(textInputControlValidator, mainPic, DeliveryItem.class, "mainPic", resourceBundle));
      secondaryPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<DeliveryItem>(textInputControlValidator, secondaryPic, DeliveryItem.class, "secondaryPic", resourceBundle));
      articleName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<DeliveryItem>(textInputControlValidator, articleName, DeliveryItem.class, "articleName", resourceBundle));
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<DeliveryItem>> validate(DeliveryItem model)
   {
      Set<ConstraintViolation<DeliveryItem>> violations = new HashSet<ConstraintViolation<DeliveryItem>>();
      violations.addAll(textInputControlValidator.validate(internalPic, DeliveryItem.class, "internalPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(mainPic, DeliveryItem.class, "mainPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(secondaryPic, DeliveryItem.class, "secondaryPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(articleName, DeliveryItem.class, "articleName", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(deliveryItemArticleSelection.getArticle(), model.getArticle(), DeliveryItem.class, "article", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(deliveryItemCreatingUserSelection.getCreatingUser(), model.getCreatingUser(), DeliveryItem.class, "creatingUser", resourceBundle));
      return violations;
   }

   public void bind(DeliveryItem model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      mainPic.textProperty().bindBidirectional(model.mainPicProperty());
      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      qtyOrdered.numberProperty().bindBidirectional(model.qtyOrderedProperty());
      availableQty.numberProperty().bindBidirectional(model.availableQtyProperty());
      freeQuantity.numberProperty().bindBidirectional(model.freeQuantityProperty());
      stockQuantity.numberProperty().bindBidirectional(model.stockQuantityProperty());
      salesPricePU.numberProperty().bindBidirectional(model.salesPricePUProperty());
      purchasePricePU.numberProperty().bindBidirectional(model.purchasePricePUProperty());
      totalPurchasePrice.numberProperty().bindBidirectional(model.totalPurchasePriceProperty());
      creationDate.calendarProperty().bindBidirectional(model.creationDateProperty());
      expirationDate.calendarProperty().bindBidirectional(model.expirationDateProperty());
      deliveryItemDeliveryForm.bind(model);
      deliveryItemArticleForm.bind(model);
      deliveryItemArticleSelection.bind(model);
      deliveryItemCreatingUserForm.bind(model);
      deliveryItemCreatingUserSelection.bind(model);
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

   public CalendarTextField getCreationDate()
   {
      return creationDate;
   }

   public CalendarTextField getExpirationDate()
   {
      return expirationDate;
   }

   public DeliveryItemDeliveryForm getDeliveryItemDeliveryForm()
   {
      return deliveryItemDeliveryForm;
   }

   public DeliveryItemArticleForm getDeliveryItemArticleForm()
   {
      return deliveryItemArticleForm;
   }

   public DeliveryItemArticleSelection getDeliveryItemArticleSelection()
   {
      return deliveryItemArticleSelection;
   }

   public DeliveryItemCreatingUserForm getDeliveryItemCreatingUserForm()
   {
      return deliveryItemCreatingUserForm;
   }

   public DeliveryItemCreatingUserSelection getDeliveryItemCreatingUserSelection()
   {
      return deliveryItemCreatingUserSelection;
   }
}
