package org.adorsys.adpharma.client.jpa.warehouse;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class WareHouseRemoveService extends Service<WareHouse>
{

   @Inject
   private WareHouseService remoteService;

   private WareHouse entity;

   public WareHouseRemoveService setEntity(WareHouse entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<WareHouse> createTask()
   {
      return new Task<WareHouse>()
      {
         @Override
         protected WareHouse call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
