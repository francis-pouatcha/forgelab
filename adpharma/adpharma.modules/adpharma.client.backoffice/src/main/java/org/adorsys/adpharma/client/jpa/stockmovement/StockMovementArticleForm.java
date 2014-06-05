package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class StockMovementArticleForm extends AbstractToOneAssociation<StockMovement, Article>
{

   private TextField articleName;

   private TextField pic;

   private TextField manufacturer;

   private CheckBox active;

   private CheckBox authorizedSale;

   private BigDecimalField qtyInStock;

   private BigDecimalField sppu;

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
      qtyInStock = viewBuilder.addBigDecimalField("Article_qtyInStock_description.title", "qtyInStock", resourceBundle, NumberType.INTEGER, locale);
      sppu = viewBuilder.addBigDecimalField("Article_sppu_description.title", "sppu", resourceBundle, NumberType.INTEGER, locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(StockMovement model)
   {
      articleName.textProperty().bindBidirectional(model.getArticle().articleNameProperty());
      pic.textProperty().bindBidirectional(model.getArticle().picProperty());
      manufacturer.textProperty().bindBidirectional(model.getArticle().manufacturerProperty());
      active.textProperty().bindBidirectional(model.getArticle().activeProperty(), new BooleanStringConverter());
      authorizedSale.textProperty().bindBidirectional(model.getArticle().authorizedSaleProperty(), new BooleanStringConverter());
      qtyInStock.numberProperty().bindBidirectional(model.getArticle().qtyInStockProperty());
      sppu.numberProperty().bindBidirectional(model.getArticle().sppuProperty());
   }

   public void update(StockMovementArticle data)
   {
      articleName.textProperty().set(data.articleNameProperty().get());
      pic.textProperty().set(data.picProperty().get());
      manufacturer.textProperty().set(data.manufacturerProperty().get());
      active.textProperty().set(new BooleanStringConverter().toString(data.activeProperty().get()));
      authorizedSale.textProperty().set(new BooleanStringConverter().toString(data.authorizedSaleProperty().get()));
      qtyInStock.numberProperty().set(data.qtyInStockProperty().get());
      sppu.numberProperty().set(data.sppuProperty().get());
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

   public BigDecimalField getQtyInStock()
   {
      return qtyInStock;
   }

   public BigDecimalField getSppu()
   {
      return sppu;
   }
}
