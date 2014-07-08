package org.adorsys.adpharma.client.jpa.directsales;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class DirectSalesIntialScreenController extends InitialScreenController<DirectSales>
{
   @Override
   public DirectSales newEntity()
   {
      return new DirectSales();
   }
}
