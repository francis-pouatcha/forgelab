package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.adpharma.client.jpa.article.ArticleSectionForm;
import org.adorsys.adpharma.client.jpa.article.ArticleSectionSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.adpharma.client.jpa.article.ArticleFamilyForm;
import org.adorsys.adpharma.client.jpa.article.ArticleFamilySelection;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import java.text.NumberFormat;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.article.ArticleDefaultSalesMarginForm;
import org.adorsys.adpharma.client.jpa.article.ArticleDefaultSalesMarginSelection;
import org.adorsys.adpharma.client.jpa.article.ArticlePackagingModeForm;
import org.adorsys.adpharma.client.jpa.article.ArticlePackagingModeSelection;
import org.adorsys.adpharma.client.jpa.article.ArticleAgencyForm;
import org.adorsys.adpharma.client.jpa.article.ArticleAgencySelection;
import org.adorsys.adpharma.client.jpa.article.ArticleClearanceConfigForm;
import org.adorsys.adpharma.client.jpa.article.ArticleClearanceConfigSelection;
import org.adorsys.adpharma.client.jpa.article.ArticleAgency;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleLotArticleForm extends AbstractToOneAssociation<ArticleLot, Article>
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

   public void bind(ArticleLot model)
   {
      articleName.textProperty().bindBidirectional(model.getArticle().articleNameProperty());
      pic.textProperty().bindBidirectional(model.getArticle().picProperty());
      manufacturer.textProperty().bindBidirectional(model.getArticle().manufacturerProperty());
      active.textProperty().bindBidirectional(model.getArticle().activeProperty(), new BooleanStringConverter());
      authorizedSale.textProperty().bindBidirectional(model.getArticle().authorizedSaleProperty(), new BooleanStringConverter());
      qtyInStock.numberProperty().bindBidirectional(model.getArticle().qtyInStockProperty());
      sppu.numberProperty().bindBidirectional(model.getArticle().sppuProperty());
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
