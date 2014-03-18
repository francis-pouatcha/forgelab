package org.adorsys.adpharma.client.jpa.permissionactionenum;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class PermissionActionEnumListCellFatory extends ListCellFactory<PermissionActionEnum>
{
   @Override
   public ListCell<PermissionActionEnum> newListCell(ResourceBundle resourceBundle)
   {
      return new PermissionActionEnumListCell(resourceBundle);
   }
}
