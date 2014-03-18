package org.adorsys.adpharma.client.jpa.workgroup;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class WorkGroupListCellFatory extends ListCellFactory<WorkGroup>
{
   @Override
   public ListCell<WorkGroup> newListCell(ResourceBundle resourceBundle)
   {
      return new WorkGroupListCell(resourceBundle);
   }
}
