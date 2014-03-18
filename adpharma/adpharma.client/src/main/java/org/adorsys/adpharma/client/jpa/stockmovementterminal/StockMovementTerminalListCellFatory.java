package org.adorsys.adpharma.client.jpa.stockmovementterminal;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class StockMovementTerminalListCellFatory extends ListCellFactory<StockMovementTerminal>
{
   @Override
   public ListCell<StockMovementTerminal> newListCell(ResourceBundle resourceBundle)
   {
      return new StockMovementTerminalListCell(resourceBundle);
   }
}
