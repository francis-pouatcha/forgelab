package org.adorsys.adpharma.client.jpa.customervoucher;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.agency.Agency;

public class CustomerVoucherAgencyListCell extends AbstractToStringListCell<Agency>
{

   @Override
   protected String getToString(Agency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
