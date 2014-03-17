package org.adorsys.adpharma.client.jpa.procurementorderitem;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;

public class ProcurementOrderItemViewSearchFields extends AbstractForm<ProcurementOrderItem>
{

   private TextField lineIndex;

   private TextField internalPic;

   private TextField mainPic;

   private TextField secondaryPic;

   private TextField articleName;

   private CheckBox valid;

   @Inject
   @Bundle({ CrudKeys.class, ProcurementOrderItem.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      lineIndex = viewBuilder.addTextField("ProcurementOrderItem_lineIndex_description.title", "lineIndex", resourceBundle);
      internalPic = viewBuilder.addTextField("ProcurementOrderItem_internalPic_description.title", "internalPic", resourceBundle);
      mainPic = viewBuilder.addTextField("ProcurementOrderItem_mainPic_description.title", "mainPic", resourceBundle);
      secondaryPic = viewBuilder.addTextField("ProcurementOrderItem_secondaryPic_description.title", "secondaryPic", resourceBundle);
      articleName = viewBuilder.addTextField("ProcurementOrderItem_articleName_description.title", "articleName", resourceBundle);
      valid = viewBuilder.addCheckBox("ProcurementOrderItem_valid_description.title", "valid", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrderItem model)
   {
      lineIndex.textProperty().bindBidirectional(model.lineIndexProperty());
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
      mainPic.textProperty().bindBidirectional(model.mainPicProperty());
      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
      articleName.textProperty().bindBidirectional(model.articleNameProperty());
      valid.textProperty().bindBidirectional(model.validProperty(), new BooleanStringConverter());

   }

   public TextField getLineIndex()
   {
      return lineIndex;
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

   public CheckBox getValid()
   {
      return valid;
   }
}
