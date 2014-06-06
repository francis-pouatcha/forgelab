package org.adorsys.adpharma.client.jpa.supplierinvoice;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class SupplierInvoiceAgencyListCell extends AbstractToStringListCell<SupplierInvoiceAgency>
{

   @Override
   protected String getToString(SupplierInvoiceAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
