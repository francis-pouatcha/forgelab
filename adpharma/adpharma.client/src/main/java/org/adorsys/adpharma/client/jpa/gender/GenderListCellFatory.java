package org.adorsys.adpharma.client.jpa.gender;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class GenderListCellFatory extends ListCellFactory<Gender>
{
   @Override
   public ListCell<Gender> newListCell(ResourceBundle resourceBundle)
   {
      return new GenderListCell(resourceBundle);
   }
}
