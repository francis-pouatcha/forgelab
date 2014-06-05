package org.adorsys.adpharma.client.jpa.article;

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

import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;

public class ArticleDefaultSalesMarginForm extends AbstractToOneAssociation<Article, SalesMargin>
{

   private TextField name;

   private CheckBox active;

   private BigDecimalField rate;

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
      rate = viewBuilder.addBigDecimalField("SalesMargin_rate_description.title", "rate", resourceBundle, NumberType.PERCENTAGE, locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      name.textProperty().bindBidirectional(model.getDefaultSalesMargin().nameProperty());
      active.textProperty().bindBidirectional(model.getDefaultSalesMargin().activeProperty(), new BooleanStringConverter());
      rate.numberProperty().bindBidirectional(model.getDefaultSalesMargin().rateProperty());
   }

   public void update(ArticleDefaultSalesMargin data)
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
