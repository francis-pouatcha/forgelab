package org.adorsys.adpharma.client.jpa.customertype;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class CustomerTypeListCellFatory extends ListCellFactory<CustomerType>
{
   @Override
   public ListCell<CustomerType> newListCell(ResourceBundle resourceBundle)
   {
      return new CustomerTypeListCell(resourceBundle);
   }
}
