package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.List;
import java.util.ResourceBundle;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementType;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminal;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.ComboBox;
import org.adorsys.javafx.crud.extensions.validation.ComboBoxValidator;
import org.adorsys.javafx.crud.extensions.validation.ComboBoxFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.stockmovement.StockMovement;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementTypeConverter;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementTypeListCellFatory;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminalConverter;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminalListCellFatory;

public class StockMovementViewSearchFields extends AbstractForm<StockMovement>
{

   private TextField originatedDocNumber;

   private TextField internalPic;

   @Inject
   @Bundle({ CrudKeys.class, StockMovement.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(StockMovementType.class)
   private ResourceBundle movementTypeBundle;

   @Inject
   private StockMovementTypeConverter movementTypeConverter;

   @Inject
   private StockMovementTypeListCellFatory movementTypeListCellFatory;
   @Inject
   @Bundle(StockMovementTerminal.class)
   private ResourceBundle movementOriginBundle;

   @Inject
   private StockMovementTerminalConverter movementOriginConverter;

   @Inject
   private StockMovementTerminalListCellFatory movementOriginListCellFatory;
   @Inject
   @Bundle(StockMovementTerminal.class)
   private ResourceBundle movementDestinationBundle;

   @Inject
   private StockMovementTerminalConverter movementDestinationConverter;

   @Inject
   private StockMovementTerminalListCellFatory movementDestinationListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      originatedDocNumber = viewBuilder.addTextField("StockMovement_originatedDocNumber_description.title", "originatedDocNumber", resourceBundle);
      internalPic = viewBuilder.addTextField("StockMovement_internalPic_description.title", "internalPic", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(StockMovement model)
   {
      originatedDocNumber.textProperty().bindBidirectional(model.originatedDocNumberProperty());
      internalPic.textProperty().bindBidirectional(model.internalPicProperty());

   }

   public TextField getOriginatedDocNumber()
   {
      return originatedDocNumber;
   }

   public TextField getInternalPic()
   {
      return internalPic;
   }
}
