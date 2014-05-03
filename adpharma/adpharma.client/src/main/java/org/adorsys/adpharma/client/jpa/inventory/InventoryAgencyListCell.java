package org.adorsys.adpharma.client.jpa.inventory;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class InventoryAgencyListCell extends AbstractToStringListCell<InventoryAgency>
{

   @Override
   protected String getToString(InventoryAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
