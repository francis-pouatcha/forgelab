package org.adorsys.adpharma.client.jpa.procurementorder;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public class ProcurementOrderSupplierListCell extends AbstractToStringListCell<ProcurementOrderSupplier>
{

   @Override
   protected String getToString(ProcurementOrderSupplier item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
