package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class SalesOrderAgencyListCell extends AbstractToStringListCell<SalesOrderAgency>
{

   @Override
   protected String getToString(SalesOrderAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
