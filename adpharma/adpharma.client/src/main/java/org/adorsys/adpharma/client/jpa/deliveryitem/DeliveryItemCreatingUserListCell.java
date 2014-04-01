package org.adorsys.adpharma.client.jpa.deliveryitem;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class DeliveryItemCreatingUserListCell extends AbstractToStringListCell<DeliveryItemCreatingUser>
{

   @Override
   protected String getToString(DeliveryItemCreatingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
