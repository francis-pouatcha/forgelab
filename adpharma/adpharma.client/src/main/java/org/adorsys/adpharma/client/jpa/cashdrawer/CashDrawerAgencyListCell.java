package org.adorsys.adpharma.client.jpa.cashdrawer;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.agency.Agency;

public class CashDrawerAgencyListCell extends AbstractToStringListCell<CashDrawerAgency>
{

   @Override
   protected String getToString(CashDrawerAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
