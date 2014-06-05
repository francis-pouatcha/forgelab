package org.adorsys.adpharma.client.jpa.delivery;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class DeliveryCurrencyListCell extends AbstractToStringListCell<DeliveryCurrency>
{

   @Override
   protected String getToString(DeliveryCurrency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name", "cfaEquivalent");
   }

}
