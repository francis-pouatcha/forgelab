package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.vat.VAT;

public class DeliveryVatForm extends AbstractToOneAssociation<Delivery, VAT>
{

   private TextField code;

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
      code = viewBuilder.addTextField("VAT_code_description.title", "code", resourceBundle);
      active = viewBuilder.addCheckBox("VAT_active_description.title", "active", resourceBundle);
      rate = viewBuilder.addBigDecimalField("VAT_rate_description.title", "rate", resourceBundle, NumberType.PERCENTAGE, locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
      code.textProperty().bindBidirectional(model.getVat().codeProperty());
      active.textProperty().bindBidirectional(model.getVat().activeProperty(), new BooleanStringConverter());
      rate.numberProperty().bindBidirectional(model.getVat().rateProperty());
   }

   public TextField getCode()
   {
      return code;
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
