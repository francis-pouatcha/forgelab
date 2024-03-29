package org.adorsys.adpharma.client.jpa.delivery;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class DeliveryCreatingUserListCell extends AbstractToStringListCell<DeliveryCreatingUser>
{

   @Override
   protected String getToString(DeliveryCreatingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
