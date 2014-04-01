package org.adorsys.adpharma.client.jpa.delivery;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public class DeliverySupplierListCell extends AbstractToStringListCell<DeliverySupplier>
{

   @Override
   protected String getToString(DeliverySupplier item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
