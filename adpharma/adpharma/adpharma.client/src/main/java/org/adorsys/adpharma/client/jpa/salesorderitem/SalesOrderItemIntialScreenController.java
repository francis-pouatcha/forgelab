package org.adorsys.adpharma.client.jpa.salesorderitem;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class SalesOrderItemIntialScreenController extends InitialScreenController<SalesOrderItem>
{
   @Override
   public SalesOrderItem newEntity()
   {
      return new SalesOrderItem();
   }
}
