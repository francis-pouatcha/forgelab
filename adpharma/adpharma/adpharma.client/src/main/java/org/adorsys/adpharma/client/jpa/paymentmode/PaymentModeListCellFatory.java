package org.adorsys.adpharma.client.jpa.paymentmode;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;

import org.adorsys.javafx.crud.extensions.view.ListCellFactory;

public class PaymentModeListCellFatory extends ListCellFactory<PaymentMode>
{
   @Override
   public ListCell<PaymentMode> newListCell(ResourceBundle resourceBundle)
   {
      return new PaymentModeListCell(resourceBundle);
   }
}
