package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;
import java.util.ResourceBundle;

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

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.currency.Currency;

public class DeliveryCurrencyForm extends AbstractToOneAssociation<Delivery, Currency>
{

   private TextField name;

   private BigDecimalField cfaEquivalent;

   @Inject
   @Bundle({ CrudKeys.class, Currency.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("Currency_name_description.title", "name", resourceBundle);
      cfaEquivalent = viewBuilder.addBigDecimalField("Currency_cfaEquivalent_description.title", "cfaEquivalent", resourceBundle, NumberType.CURRENCY, locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
      name.textProperty().bindBidirectional(model.getCurrency().nameProperty());
      cfaEquivalent.numberProperty().bindBidirectional(model.getCurrency().cfaEquivalentProperty());
   }

   public void update(DeliveryCurrency data)
   {
      name.textProperty().set(data.nameProperty().get());
      cfaEquivalent.numberProperty().set(data.cfaEquivalentProperty().get());
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
