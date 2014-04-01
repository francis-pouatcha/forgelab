package org.adorsys.adpharma.client.jpa.inventoryitem;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class InventoryItemRecordingUserListCell extends AbstractToStringListCell<InventoryItemRecordingUser>
{

   @Override
   protected String getToString(InventoryItemRecordingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
