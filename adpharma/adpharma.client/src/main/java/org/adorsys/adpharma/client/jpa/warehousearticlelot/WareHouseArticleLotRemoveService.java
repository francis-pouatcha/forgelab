package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class WareHouseArticleLotRemoveService extends Service<WareHouseArticleLot>
{

   @Inject
   private WareHouseArticleLotService remoteService;

   private WareHouseArticleLot entity;

   public WareHouseArticleLotRemoveService setEntity(WareHouseArticleLot entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<WareHouseArticleLot> createTask()
   {
      return new Task<WareHouseArticleLot>()
      {
         @Override
         protected WareHouseArticleLot call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
