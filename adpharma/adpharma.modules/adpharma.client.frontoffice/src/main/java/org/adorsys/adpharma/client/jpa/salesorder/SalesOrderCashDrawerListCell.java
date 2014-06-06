package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class SalesOrderCashDrawerListCell extends AbstractToStringListCell<SalesOrderCashDrawer>
{

   @Override
   protected String getToString(SalesOrderCashDrawer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "cashDrawerNumber");
   }

}
