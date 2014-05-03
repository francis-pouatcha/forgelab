package org.adorsys.adpharma.client.jpa.inventory;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class InventoryRecordingUserListCell extends AbstractToStringListCell<InventoryRecordingUser>
{

   @Override
   protected String getToString(InventoryRecordingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
