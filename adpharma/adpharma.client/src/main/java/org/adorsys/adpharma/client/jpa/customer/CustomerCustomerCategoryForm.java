package org.adorsys.adpharma.client.jpa.customer;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.TextArea;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;

public class CustomerCustomerCategoryForm extends AbstractToOneAssociation<Customer, CustomerCategory>
{

   private TextField name;

   private BigDecimalField discountRate;

   @Inject
   @Bundle({ CrudKeys.class, CustomerCategory.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("CustomerCategory_name_description.title", "name", resourceBundle);
      discountRate = viewBuilder.addBigDecimalField("CustomerCategory_discountRate_description.title", "discountRate", resourceBundle, NumberType.PERCENTAGE, locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Customer model)
   {
      name.textProperty().bindBidirectional(model.getCustomerCategory().nameProperty());
      discountRate.numberProperty().bindBidirectional(model.getCustomerCategory().discountRateProperty());
   }

   public TextField getName()
   {
      return name;
   }

   public BigDecimalField getDiscountRate()
   {
      return discountRate;
   }
}
