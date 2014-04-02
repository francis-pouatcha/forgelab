package org.adorsys.adpharma.client.jpa.article;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ArticleViewSearchFields extends AbstractForm<Article>
{

   private TextField articleName;

   private TextField pic;

   private TextField manufacturer;

   private CheckBox active;

   private CheckBox authorizedSale;

   private BigDecimalField maxStockQty;

   @Inject
   @Bundle({ CrudKeys.class, Article.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      articleName = viewBuilder.addTextField("Article_articleName_description.title", "articleName", resourceBundle);
      pic = viewBuilder.addTextField("Article_pic_description.title", "pic", resourceBundle);
      manufacturer = viewBuilder.addTextField("Article_manufacturer_description.title", "manufacturer", resourceBundle);
      active = viewBuilder.addCheckBox("Article_active_description.title", "active", resourceBundle);
      authorizedSale = viewBuilder.addCheckBox("Article_authorizedSale_description.title", "authorizedSale", resourceBundle);
      maxStockQty = viewBuilder.addBigDecimalField("Article_maxStockQty_description.title", "maxStockQty", resourceBundle, NumberType.INTEGER, locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      pic.textProperty().bindBidirectional(model.picProperty());
      manufacturer.textProperty().bindBidirectional(model.manufacturerProperty());
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());
      authorizedSale.textProperty().bindBidirectional(model.authorizedSaleProperty(), new BooleanStringConverter());
      maxStockQty.numberProperty().bindBidirectional(model.maxStockQtyProperty());

   }

   public TextField getArticleName()
   {
      return articleName;
   }

   public TextField getPic()
   {
      return pic;
   }

   public TextField getManufacturer()
   {
      return manufacturer;
   }

   public CheckBox getActive()
   {
      return active;
   }

   public CheckBox getAuthorizedSale()
   {
      return authorizedSale;
   }

   public TextField getMaxStockQty()
   {
      return maxStockQty;
   }
}
