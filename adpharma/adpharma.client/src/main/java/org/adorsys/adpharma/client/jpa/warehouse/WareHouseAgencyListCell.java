package org.adorsys.adpharma.client.jpa.warehouse;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class WareHouseAgencyListCell extends AbstractToStringListCell<WareHouseAgency>
{

   @Override
   protected String getToString(WareHouseAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name", "agency");
   }

}
