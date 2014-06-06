package org.adorsys.adpharma.client.jpa.login;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class LoginAgencyListCell extends AbstractToStringListCell<LoginAgency>
{

   @Override
   protected String getToString(LoginAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
