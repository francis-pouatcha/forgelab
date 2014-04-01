package org.adorsys.adpharma.client.jpa.delivery;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.vat.VAT;

public class DeliveryVatListCell extends AbstractToStringListCell<DeliveryVat>
{

   @Override
   protected String getToString(DeliveryVat item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
