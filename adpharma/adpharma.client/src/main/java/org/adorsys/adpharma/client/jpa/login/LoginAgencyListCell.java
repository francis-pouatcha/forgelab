package org.adorsys.adpharma.client.jpa.login;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class LoginAgencyListCell extends AbstractToStringListCell<Agency>
{

   @Override
   protected String getToString(Agency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber","name");
   }

}
