package org.adorsys.adpharma.client.jpa.accessroleenum;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class AccessRoleEnumListCellFatory extends ListCellFactory<AccessRoleEnum>
{
   @Override
   public ListCell<AccessRoleEnum> newListCell(ResourceBundle resourceBundle)
   {
      return new AccessRoleEnumListCell(resourceBundle);
   }
}
