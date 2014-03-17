package org.adorsys.adpharma.client.jpa.supplierinvoice;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public class SupplierInvoiceSupplierListCell extends AbstractToStringListCell<Supplier>
{

   @Override
   protected String getToString(Supplier item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
