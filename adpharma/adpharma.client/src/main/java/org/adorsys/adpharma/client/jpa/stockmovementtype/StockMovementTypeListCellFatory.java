package org.adorsys.adpharma.client.jpa.stockmovementtype;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class StockMovementTypeListCellFatory extends ListCellFactory<StockMovementType>
{
   @Override
   public ListCell<StockMovementType> newListCell(ResourceBundle resourceBundle)
   {
      return new StockMovementTypeListCell(resourceBundle);
   }
}
