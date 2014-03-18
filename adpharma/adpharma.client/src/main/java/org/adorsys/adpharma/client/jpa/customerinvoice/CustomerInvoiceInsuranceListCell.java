package org.adorsys.adpharma.client.jpa.customerinvoice;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;

public class CustomerInvoiceInsuranceListCell extends AbstractToStringListCell<Insurrance>
{

   @Override
   protected String getToString(Insurrance item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "fullName", "fullName", "coverageRate");
   }

}
