package org.adorsys.adpharma.client.jpa.warehouse;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class WareHouseLoadService extends Service<WareHouse>
{

   @Inject
   private WareHouseService remoteService;

   private Long id;

   public WareHouseLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
