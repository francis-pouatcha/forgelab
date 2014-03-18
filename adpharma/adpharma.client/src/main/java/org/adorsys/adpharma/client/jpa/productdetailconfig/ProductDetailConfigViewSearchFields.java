package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.List;
import java.util.ResourceBundle;

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

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;

public class ProductDetailConfigViewSearchFields extends AbstractForm<ProductDetailConfig>
{

   private CheckBox active;

   @Inject
   @Bundle({ CrudKeys.class, ProductDetailConfig.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      active = viewBuilder.addCheckBox("ProductDetailConfig_active_description.title", "active", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProductDetailConfig model)
   {
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());

   }

   public CheckBox getActive()
   {
      return active;
   }
}
