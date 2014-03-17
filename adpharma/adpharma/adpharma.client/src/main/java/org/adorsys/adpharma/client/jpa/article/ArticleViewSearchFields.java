package org.adorsys.adpharma.client.jpa.article;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.section.Section;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;
import java.math.BigDecimal;
import javafx.beans.property.SimpleLongProperty;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javaext.format.NumberType;
import java.util.Locale;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import java.text.NumberFormat;
import jfxtras.scene.control.CalendarTextField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleViewSearchFields extends AbstractForm<Article>
{

   private TextField articleName;

   private TextField pic;

   private TextField manufacturer;

   private CheckBox active;

   private TextField maxQtyPerPO;

   private CheckBox authorizedSale;

   private CheckBox approvedOrder;

   private TextField maxStockQty;

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
      maxQtyPerPO = viewBuilder.addTextField("Article_maxQtyPerPO_description.title", "maxQtyPerPO", resourceBundle);
      authorizedSale = viewBuilder.addCheckBox("Article_authorizedSale_description.title", "authorizedSale", resourceBundle);
      approvedOrder = viewBuilder.addCheckBox("Article_approvedOrder_description.title", "approvedOrder", resourceBundle);
      maxStockQty = viewBuilder.addTextField("Article_maxStockQty_description.title", "maxStockQty", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(Article model)
   {
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      pic.textProperty().bindBidirectional(model.picProperty());
      manufacturer.textProperty().bindBidirectional(model.manufacturerProperty());
      active.textProperty().bindBidirectional(model.activeProperty(), new BooleanStringConverter());
      maxQtyPerPO.textProperty().bindBidirectional(model.maxQtyPerPOProperty(), NumberFormat.getInstance(locale));
      authorizedSale.textProperty().bindBidirectional(model.authorizedSaleProperty(), new BooleanStringConverter());
      approvedOrder.textProperty().bindBidirectional(model.approvedOrderProperty(), new BooleanStringConverter());
      maxStockQty.textProperty().bindBidirectional(model.maxStockQtyProperty(), NumberFormat.getInstance(locale));

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

   public TextField getMaxQtyPerPO()
   {
      return maxQtyPerPO;
   }

   public CheckBox getAuthorizedSale()
   {
      return authorizedSale;
   }

   public CheckBox getApprovedOrder()
   {
      return approvedOrder;
   }

   public TextField getMaxStockQty()
   {
      return maxStockQty;
   }
}
