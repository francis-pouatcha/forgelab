package org.adorsys.adpharma.client.jpa.delivery;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class DeliveryReceivingAgencyListCell extends AbstractToStringListCell<DeliveryReceivingAgency>
{

   @Override
   protected String getToString(DeliveryReceivingAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
