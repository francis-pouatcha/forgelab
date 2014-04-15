package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import java.math.BigDecimal;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;

public class ProductDetailConfigView extends AbstractForm<ProductDetailConfig>
{

   private CheckBox active;

   private BigDecimalField targetQuantity;

   private BigDecimalField salesPrice;

   private CalendarTextField recordingDate;

   @Inject
   private ProductDetailConfigSourceForm productDetailConfigSourceForm;
   @Inject
   private ProductDetailConfigSourceSelection productDetailConfigSourceSelection;

   @Inject
   private ProductDetailConfigTargetForm productDetailConfigTargetForm;
   @Inject
   private ProductDetailConfigTargetSelection productDetailConfigTargetSelection;

   @Inject
   @Bundle({ CrudKeys.class, ProductDetailConfig.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private BigDecimalFieldValidator bigDecimalFieldValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      viewBuilder.addTitlePane("ProductDetailConfig_source_description.title", resourceBundle);
      viewBuilder.addSubForm("ProductDetailConfig_source_description.title", "source", resourceBundle, productDetailConfigSourceForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProductDetailConfig_source_description.title", "source", resourceBundle, productDetailConfigSourceSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("ProductDetailConfig_target_description.title", resourceBundle);
      viewBuilder.addSubForm("ProductDetailConfig_target_description.title", "target", resourceBundle, productDetailConfigTargetForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ProductDetailConfig_target_description.title", "target", resourceBundle, productDetailConfigTargetSelection, ViewModel.READ_WRITE);

      targetQuantity = viewBuilder.addBigDecimalField("ProductDetailConfig_targetQuantity_description.title", "targetQuantity", resourceBundle, NumberType.INTEGER, locale);
      salesPrice = viewBuilder.addBigDecimalField("ProductDetailConfig_salesPrice_description.title", "salesPrice", resourceBundle, NumberType.CURRENCY, locale);
      active = viewBuilder.addCheckBox("ProductDetailConfig_active_description.title", "active", resourceBundle);
      recordingDate = viewBuilder.addCalendarTextField("ProductDetailConfig_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale,ViewModel.READ_ONLY);
      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      targetQuantity.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<ProductDetailConfig>(bigDecimalFieldValidator, targetQuantity, ProductDetailConfig.class, "targetQuantity", resourceBundle));
      salesPrice.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<ProductDetailConfig>(bigDecimalFieldValidator, salesPrice, ProductDetailConfig.class, "salesPrice", resourceBundle));
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<ProductDetailConfig>> validate(ProductDetailConfig model)
   {
      Set<ConstraintViolation<ProductDetailConfig>> violations = new HashSet<ConstraintViolation<ProductDetailConfig>>();
      violations.addAll(bigDecimalFieldValidator.validate(targetQuantity, ProductDetailConfig.class, "targetQuantity", resourceBundle));
      violations.addAll(bigDecimalFieldValidator.validate(salesPrice, ProductDetailConfig.class, "salesPrice", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(productDetailConfigSourceSelection.getSource(), model.getSource(), ProductDetailConfig.class, "source", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(productDetailConfigTargetSelection.getTarget(), model.getTarget(), ProductDetailConfig.class, "target", resourceBundle));
      return violations;
   }

   public void bind(ProductDetailConfig model)
   {
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());
      targetQuantity.numberProperty().bindBidirectional(model.targetQuantityProperty());
      salesPrice.numberProperty().bindBidirectional(model.salesPriceProperty());
      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
      productDetailConfigSourceForm.bind(model);
      productDetailConfigSourceSelection.bind(model);
      productDetailConfigTargetForm.bind(model);
      productDetailConfigTargetSelection.bind(model);
   }

   public CheckBox getActive()
   {
      return active;
   }

   public BigDecimalField getTargetQuantity()
   {
      return targetQuantity;
   }

   public BigDecimalField getSalesPrice()
   {
      return salesPrice;
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }

   public ProductDetailConfigSourceForm getProductDetailConfigSourceForm()
   {
      return productDetailConfigSourceForm;
   }

   public ProductDetailConfigSourceSelection getProductDetailConfigSourceSelection()
   {
      return productDetailConfigSourceSelection;
   }

   public ProductDetailConfigTargetForm getProductDetailConfigTargetForm()
   {
      return productDetailConfigTargetForm;
   }

   public ProductDetailConfigTargetSelection getProductDetailConfigTargetSelection()
   {
      return productDetailConfigTargetSelection;
   }
}
