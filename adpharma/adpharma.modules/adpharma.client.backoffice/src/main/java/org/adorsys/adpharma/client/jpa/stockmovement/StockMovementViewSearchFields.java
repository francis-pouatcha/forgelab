package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminal;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminalConverter;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminalListCellFatory;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementType;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementTypeConverter;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementTypeListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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
