package org.adorsys.adpharma.client.jpa.payment;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.agency.Agency;

public class PaymentAgencyListCell extends AbstractToStringListCell<PaymentAgency>
{

   @Override
   protected String getToString(PaymentAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
