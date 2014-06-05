package org.adorsys.adpharma.client.jpa.warehouse;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class WareHouseIntialScreenController extends InitialScreenController<WareHouse>
{
   @Override
   public WareHouse newEntity()
   {
      return new WareHouse();
   }
}
