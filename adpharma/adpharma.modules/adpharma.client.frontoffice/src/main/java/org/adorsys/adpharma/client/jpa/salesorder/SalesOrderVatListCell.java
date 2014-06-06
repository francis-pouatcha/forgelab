package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class SalesOrderVatListCell extends AbstractToStringListCell<SalesOrderVat>
{

   @Override
   protected String getToString(SalesOrderVat item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
