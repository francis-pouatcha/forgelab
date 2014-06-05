package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class SalesOrderIntialScreenController extends InitialScreenController<SalesOrder>
{
   @Override
   public SalesOrder newEntity()
   {
      return new SalesOrder();
   }
}
