package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class WareHouseArticleLotIntialScreenController extends InitialScreenController<WareHouseArticleLot>
{
   @Override
   public WareHouseArticleLot newEntity()
   {
      return new WareHouseArticleLot();
   }
}
