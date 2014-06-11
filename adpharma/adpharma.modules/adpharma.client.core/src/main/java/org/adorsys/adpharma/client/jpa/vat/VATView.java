package org.adorsys.adpharma.client.jpa.vat;

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

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class VATView extends AbstractForm<VAT>
{

   private TextField name;

   private CheckBox active;

   private BigDecimalField rate;

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
      rate = viewBuilder.addBigDecimalField("VAT_rate_description.title", "rate", resourceBundle, NumberType.PERCENTAGE, locale);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<VAT>> validate(VAT model)
   {
      Set<ConstraintViolation<VAT>> violations = new HashSet<ConstraintViolation<VAT>>();
      return violations;
   }

   public void bind(VAT model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());
      rate.numberProperty().bindBidirectional(model.rateProperty());
   }

   public TextField getName()
   {
      return name;
   }

   public CheckBox getActive()
   {
      return active;
   }

   public BigDecimalField getRate()
   {
      return rate;
   }
}