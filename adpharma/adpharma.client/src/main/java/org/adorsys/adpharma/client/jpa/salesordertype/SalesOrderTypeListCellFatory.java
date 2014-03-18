package org.adorsys.adpharma.client.jpa.salesordertype;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class SalesOrderTypeListCellFatory extends ListCellFactory<SalesOrderType>
{
   @Override
   public ListCell<SalesOrderType> newListCell(ResourceBundle resourceBundle)
   {
      return new SalesOrderTypeListCell(resourceBundle);
   }
}
