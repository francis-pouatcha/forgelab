package org.adorsys.adpharma.client.jpa.invoicetype;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class InvoiceTypeListCellFatory extends ListCellFactory<InvoiceType>
{
   @Override
   public ListCell<InvoiceType> newListCell(ResourceBundle resourceBundle)
   {
      return new InvoiceTypeListCell(resourceBundle);
   }
}
