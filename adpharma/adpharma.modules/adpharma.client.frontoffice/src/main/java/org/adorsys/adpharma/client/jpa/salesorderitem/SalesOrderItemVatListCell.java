package org.adorsys.adpharma.client.jpa.salesorderitem;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class SalesOrderItemVatListCell extends AbstractToStringListCell<SalesOrderItemVat>
{

   @Override
   protected String getToString(SalesOrderItemVat item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
