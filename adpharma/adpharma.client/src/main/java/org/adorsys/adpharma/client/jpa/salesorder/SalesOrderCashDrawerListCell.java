package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class SalesOrderCashDrawerListCell extends AbstractToStringListCell<CashDrawer>
{

   @Override
   protected String getToString(CashDrawer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "cashDrawerNumber");
   }

}
