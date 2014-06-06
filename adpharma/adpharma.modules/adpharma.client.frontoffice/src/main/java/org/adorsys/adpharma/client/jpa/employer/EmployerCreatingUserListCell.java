package org.adorsys.adpharma.client.jpa.employer;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class EmployerCreatingUserListCell extends AbstractToStringListCell<EmployerCreatingUser>
{

   @Override
   protected String getToString(EmployerCreatingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
