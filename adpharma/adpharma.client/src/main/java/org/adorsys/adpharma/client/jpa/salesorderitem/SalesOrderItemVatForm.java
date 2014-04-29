package org.adorsys.adpharma.client.jpa.salesorderitem;

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

public class SalesOrderItemVatForm extends AbstractToOneAssociation<SalesOrderItem, VAT>
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

   public void bind(SalesOrderItem model)
   {
      name.textProperty().bindBidirectional(model.getVat().nameProperty());
      active.textProperty().bindBidirectional(model.getVat().activeProperty(), new BooleanStringConverter());
      rate.numberProperty().bindBidirectional(model.getVat().rateProperty());
   }

   public void update(SalesOrderItemVat data)
   {
      name.textProperty().set(data.nameProperty().get());
      active.textProperty().set(new BooleanStringConverter().toString(data.activeProperty().get()));
      rate.numberProperty().set(data.rateProperty().get());
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
