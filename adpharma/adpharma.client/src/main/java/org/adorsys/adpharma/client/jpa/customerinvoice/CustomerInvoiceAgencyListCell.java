package org.adorsys.adpharma.client.jpa.customerinvoice;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.agency.Agency;

public class CustomerInvoiceAgencyListCell extends AbstractToStringListCell<CustomerInvoiceAgency>
{

   @Override
   protected String getToString(CustomerInvoiceAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
