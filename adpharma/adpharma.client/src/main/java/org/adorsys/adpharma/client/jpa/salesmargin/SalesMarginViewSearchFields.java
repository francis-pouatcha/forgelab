package org.adorsys.adpharma.client.jpa.salesmargin;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.control.TextField;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
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
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;

public class SalesMarginViewSearchFields extends AbstractForm<SalesMargin>
{

   private TextField name;

   private CheckBox active;

   @Inject
   @Bundle({ CrudKeys.class, SalesMargin.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("SalesMargin_name_description.title", "name", resourceBundle);
      active = viewBuilder.addCheckBox("SalesMargin_active_description.title", "active", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesMargin model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());

   }

   public TextField getName()
   {
      return name;
   }

   public CheckBox getActive()
   {
      return active;
   }
}
