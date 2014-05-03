package org.adorsys.adpharma.client.jpa.vat;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class VATViewSearchFields extends AbstractForm<VAT>
{

   private TextField name;

   private CheckBox active;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("VAT_name_description.title", "name", resourceBundle);
      active = viewBuilder.addCheckBox("VAT_active_description.title", "active", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(VAT model)
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
