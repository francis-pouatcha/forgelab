package org.adorsys.adpharma.client.jpa.procurementordertype;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class ProcurementOrderTypeListCellFatory extends ListCellFactory<ProcurementOrderType>
{
   @Override
   public ListCell<ProcurementOrderType> newListCell(ResourceBundle resourceBundle)
   {
      return new ProcurementOrderTypeListCell(resourceBundle);
   }
}
