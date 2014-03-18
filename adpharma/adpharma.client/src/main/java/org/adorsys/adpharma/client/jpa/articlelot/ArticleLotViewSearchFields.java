package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import java.util.Calendar;
import java.math.BigDecimal;

import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

public class ArticleLotViewSearchFields extends AbstractForm<ArticleLot>
{

   private TextField internalPic;

   private TextField mainPic;

   private TextField secondaryPic;

   private TextField articleName;

   @Inject
   @Bundle({ CrudKeys.class, ArticleLot.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("ArticleLot_internalPic_description.title", "internalPic", resourceBundle);
      mainPic = viewBuilder.addTextField("ArticleLot_mainPic_description.title", "mainPic", resourceBundle);
      secondaryPic = viewBuilder.addTextField("ArticleLot_secondaryPic_description.title", "secondaryPic", resourceBundle);
      articleName = viewBuilder.addTextField("ArticleLot_articleName_description.title", "articleName", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ArticleLot model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      mainPic.textProperty().bindBidirectional(model.mainPicProperty());
      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
      articleName.textProperty().bindBidirectional(model.articleNameProperty());

   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public TextField getMainPic()
   {
      return mainPic;
   }

   public TextField getSecondaryPic()
   {
      return secondaryPic;
   }

   public TextField getArticleName()
   {
      return articleName;
   }
}
