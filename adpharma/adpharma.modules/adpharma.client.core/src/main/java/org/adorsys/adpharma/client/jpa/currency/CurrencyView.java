package org.adorsys.adpharma.client.jpa.currency;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
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
import org.adorsys.adpharma.client.jpa.currency.Currency;

public class CurrencyView extends AbstractForm<Currency>
{

   private TextField name;

   private BigDecimalField cfaEquivalent;

   @Inject
   @Bundle({ CrudKeys.class, Currency.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private BigDecimalFieldValidator bigDecimalFieldValidator;
   @Inject
   private TextInputControlValidator textInputControlValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Currency_name_description.title", "name", resourceBundle);
      cfaEquivalent = viewBuilder.addBigDecimalField("Currency_cfaEquivalent_description.title", "cfaEquivalent", resourceBundle, NumberType.CURRENCY, locale);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      name.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Currency>(textInputControlValidator, name, Currency.class, "name", resourceBundle));
      cfaEquivalent.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<Currency>(bigDecimalFieldValidator, cfaEquivalent, Currency.class, "cfaEquivalent", resourceBundle));
   }

   public Set<ConstraintViolation<Currency>> validate(Currency model)
   {
      Set<ConstraintViolation<Currency>> violations = new HashSet<ConstraintViolation<Currency>>();
      violations.addAll(textInputControlValidator.validate(name, Currency.class, "name", resourceBundle));
      violations.addAll(bigDecimalFieldValidator.validate(cfaEquivalent, Currency.class, "cfaEquivalent", resourceBundle));
      return violations;
   }

   public void bind(Currency model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      cfaEquivalent.numberProperty().bindBidirectional(model.cfaEquivalentProperty());
   }

   public TextField getName()
   {
      return name;
   }

   public BigDecimalField getCfaEquivalent()
   {
      return cfaEquivalent;
   }
}
