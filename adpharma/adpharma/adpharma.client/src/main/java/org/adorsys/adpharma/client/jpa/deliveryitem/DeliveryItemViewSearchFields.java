package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.util.List;
import java.util.ResourceBundle;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

public class DeliveryItemViewSearchFields extends AbstractForm<DeliveryItem>
{

   private TextField internalPic;

   private TextField secondaryPic;

   private TextField mainPic;

   private TextField articleName;

   @Inject
   @Bundle({ CrudKeys.class, DeliveryItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      internalPic = viewBuilder.addTextField("DeliveryItem_internalPic_description.title", "internalPic", resourceBundle);
      secondaryPic = viewBuilder.addTextField("DeliveryItem_secondaryPic_description.title", "secondaryPic", resourceBundle);
      mainPic = viewBuilder.addTextField("DeliveryItem_mainPic_description.title", "mainPic", resourceBundle);
      articleName = viewBuilder.addTextField("DeliveryItem_articleName_description.title", "articleName", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(DeliveryItem model)
   {
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
      mainPic.textProperty().bindBidirectional(model.mainPicProperty());
      articleName.textProperty().bindBidirectional(model.articleNameProperty());

   }

   public TextField getInternalPic()
   {
      return internalPic;
   }

   public TextField getSecondaryPic()
   {
      return secondaryPic;
   }

   public TextField getMainPic()
   {
      return mainPic;
   }

   public TextField getArticleName()
   {
      return articleName;
   }
}
