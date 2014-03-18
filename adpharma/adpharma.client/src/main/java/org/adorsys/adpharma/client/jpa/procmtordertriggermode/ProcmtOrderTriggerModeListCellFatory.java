package org.adorsys.adpharma.client.jpa.procmtordertriggermode;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class ProcmtOrderTriggerModeListCellFatory extends ListCellFactory<ProcmtOrderTriggerMode>
{
   @Override
   public ListCell<ProcmtOrderTriggerMode> newListCell(ResourceBundle resourceBundle)
   {
      return new ProcmtOrderTriggerModeListCell(resourceBundle);
   }
}
