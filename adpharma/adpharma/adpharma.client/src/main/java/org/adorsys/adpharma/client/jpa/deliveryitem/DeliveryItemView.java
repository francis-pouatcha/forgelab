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
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
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

   private TextField secondaryPic;

   private TextField mainPic;

   private TextField articleName;

   private BigDecimalField initialQty;

   private BigDecimalField finalQty;

   private BigDecimalField totalPurchasingPrice;

   private BigDecimalField totalDiscount;

   private BigDecimalField totalSalesPrice;

   private CalendarTextField creationDate;

   private CalendarTextField productRecCreated;

   @Inject
   private DeliveryItemDeliveryForm deliveryItemDeliveryForm;

   @Inject
   private DeliveryItemArticleForm deliveryItemArticleForm;
   @Inject
   private DeliveryItemArticleSelection deliveryItemArticleSelection;

   @Inject
   @Bundle({ CrudKeys.class, DeliveryItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private BigDecimalFieldValidator bigDecimalFieldValidator;
   @Inject
   private TextInputControlValidator textInputControlValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("DeliveryItem_internalPic_description.title", "internalPic", resourceBundle);
      secondaryPic = viewBuilder.addTextField("DeliveryItem_secondaryPic_description.title", "secondaryPic", resourceBundle);
      mainPic = viewBuilder.addTextField("DeliveryItem_mainPic_description.title", "mainPic", resourceBundle);
      articleName = viewBuilder.addTextField("DeliveryItem_articleName_description.title", "articleName", resourceBundle);
      initialQty = viewBuilder.addBigDecimalField("DeliveryItem_initialQty_description.title", "initialQty", resourceBundle, NumberType.INTEGER, locale);
      finalQty = viewBuilder.addBigDecimalField("DeliveryItem_finalQty_description.title", "finalQty", resourceBundle, NumberType.INTEGER, locale);
      totalPurchasingPrice = viewBuilder.addBigDecimalField("DeliveryItem_totalPurchasingPrice_description.title", "totalPurchasingPrice", resourceBundle, NumberType.CURRENCY, locale);
      totalDiscount = viewBuilder.addBigDecimalField("DeliveryItem_totalDiscount_description.title", "totalDiscount", resourceBundle, NumberType.CURRENCY, locale);
      totalSalesPrice = viewBuilder.addBigDecimalField("DeliveryItem_totalSalesPrice_description.title", "totalSalesPrice", resourceBundle, NumberType.CURRENCY, locale);
      creationDate = viewBuilder.addCalendarTextField("DeliveryItem_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      productRecCreated = viewBuilder.addCalendarTextField("DeliveryItem_productRecCreated_description.title", "productRecCreated", resourceBundle, "dd-MM-yyyy", locale);
      viewBuilder.addTitlePane("DeliveryItem_delivery_description.title", resourceBundle);
      viewBuilder.addSubForm("DeliveryItem_delivery_description.title", "delivery", resourceBundle, deliveryItemDeliveryForm, ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("DeliveryItem_article_description.title", resourceBundle);
      viewBuilder.addSubForm("DeliveryItem_article_description.title", "article", resourceBundle, deliveryItemArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("DeliveryItem_article_description.title", "article", resourceBundle, deliveryItemArticleSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      internalPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<DeliveryItem>(textInputControlValidator, internalPic, DeliveryItem.class, "internalPic", resourceBundle));
      secondaryPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<DeliveryItem>(textInputControlValidator, secondaryPic, DeliveryItem.class, "secondaryPic", resourceBundle));
      mainPic.focusedProperty().addListener(new TextInputControlFoccusChangedListener<DeliveryItem>(textInputControlValidator, mainPic, DeliveryItem.class, "mainPic", resourceBundle));
      totalPurchasingPrice.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<DeliveryItem>(bigDecimalFieldValidator, totalPurchasingPrice, DeliveryItem.class, "totalPurchasingPrice", resourceBundle));
      // no active validator
   }

   public Set<ConstraintViolation<DeliveryItem>> validate(DeliveryItem model)
   {
      Set<ConstraintViolation<DeliveryItem>> violations = new HashSet<ConstraintViolation<DeliveryItem>>();
      violations.addAll(textInputControlValidator.validate(internalPic, DeliveryItem.class, "internalPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(secondaryPic, DeliveryItem.class, "secondaryPic", resourceBundle));
      violations.addAll(textInputControlValidator.validate(mainPic, DeliveryItem.class, "mainPic", resourceBundle));
      violations.addAll(bigDecimalFieldValidator.validate(totalPurchasingPrice, DeliveryItem.class, "totalPurchasingPrice", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(deliveryItemArticleSelection.getArticle(), model.getArticle(), DeliveryItem.class, "article", resourceBundle));
      return violations;
   }

   public void bind(DeliveryItem model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
      mainPic.textProperty().bindBidirectional(model.mainPicProperty());
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      initialQty.numberProperty().bindBidirectional(model.initialQtyProperty());
      finalQty.numberProperty().bindBidirectional(model.finalQtyProperty());
      totalPurchasingPrice.numberProperty().bindBidirectional(model.totalPurchasingPriceProperty());
      totalDiscount.numberProperty().bindBidirectional(model.totalDiscountProperty());
      totalSalesPrice.numberProperty().bindBidirectional(model.totalSalesPriceProperty());
      creationDate.calendarProperty().bindBidirectional(model.creationDateProperty());
      productRecCreated.calendarProperty().bindBidirectional(model.productRecCreatedProperty());
      deliveryItemDeliveryForm.bind(model);
      deliveryItemArticleForm.bind(model);
      deliveryItemArticleSelection.bind(model);
   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public TextField getSecondaryPic()
   {
      return secondaryPic;
   }

   public TextField getMainPic()
   {
      return mainPic;
   }

   public TextField getArticleName()
   {
      return articleName;
   }

   public BigDecimalField getInitialQty()
   {
      return initialQty;
   }

   public BigDecimalField getFinalQty()
   {
      return finalQty;
   }

   public BigDecimalField getTotalPurchasingPrice()
   {
      return totalPurchasingPrice;
   }

   public BigDecimalField getTotalDiscount()
   {
      return totalDiscount;
   }

   public BigDecimalField getTotalSalesPrice()
   {
      return totalSalesPrice;
   }

   public CalendarTextField getCreationDate()
   {
      return creationDate;
   }

   public CalendarTextField getProductRecCreated()
   {
      return productRecCreated;
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
}
