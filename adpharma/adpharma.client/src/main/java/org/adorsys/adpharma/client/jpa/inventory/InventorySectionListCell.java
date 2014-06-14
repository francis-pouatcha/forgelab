package org.adorsys.adpharma.client.jpa.inventory;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class InventorySectionListCell extends AbstractToStringListCell<InventorySection>
{

   @Override
   protected String getToString(InventorySection item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
