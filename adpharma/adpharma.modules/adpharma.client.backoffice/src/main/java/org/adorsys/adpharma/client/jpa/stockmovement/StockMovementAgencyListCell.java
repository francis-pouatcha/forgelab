package org.adorsys.adpharma.client.jpa.stockmovement;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class StockMovementAgencyListCell extends AbstractToStringListCell<StockMovementAgency>
{

   @Override
   protected String getToString(StockMovementAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
