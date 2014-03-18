package org.adorsys.adpharma.client.jpa.documentprocessingstate;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class DocumentProcessingStateListCellFatory extends ListCellFactory<DocumentProcessingState>
{
   @Override
   public ListCell<DocumentProcessingState> newListCell(ResourceBundle resourceBundle)
   {
      return new DocumentProcessingStateListCell(resourceBundle);
   }
}
