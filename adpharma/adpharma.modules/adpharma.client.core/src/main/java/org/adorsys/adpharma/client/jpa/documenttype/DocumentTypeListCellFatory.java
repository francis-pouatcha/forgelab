package org.adorsys.adpharma.client.jpa.documenttype;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class DocumentTypeListCellFatory extends ListCellFactory<DocumentType>
{
   @Override
   public ListCell<DocumentType> newListCell(ResourceBundle resourceBundle)
   {
      return new DocumentTypeListCell(resourceBundle);
   }
}
