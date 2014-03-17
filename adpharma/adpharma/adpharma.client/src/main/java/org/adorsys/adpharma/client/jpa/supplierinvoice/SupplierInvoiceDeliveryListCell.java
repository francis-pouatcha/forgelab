package org.adorsys.adpharma.client.jpa.supplierinvoice;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;

public class SupplierInvoiceDeliveryListCell extends AbstractToStringListCell<Delivery>
{

   @Override
   protected String getToString(Delivery item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "deliveryNumber");
   }

}
