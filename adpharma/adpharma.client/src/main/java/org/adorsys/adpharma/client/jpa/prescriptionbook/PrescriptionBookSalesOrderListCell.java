package org.adorsys.adpharma.client.jpa.prescriptionbook;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public class PrescriptionBookSalesOrderListCell extends AbstractToStringListCell<SalesOrder>
{

   @Override
   protected String getToString(SalesOrder item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "soNumber");
   }

}
