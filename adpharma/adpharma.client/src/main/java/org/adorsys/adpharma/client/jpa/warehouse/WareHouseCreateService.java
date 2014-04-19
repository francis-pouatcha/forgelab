package org.adorsys.adpharma.client.jpa.warehouse;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WareHouseCreateService extends Service<WareHouse>
{

   private WareHouse model;

   @Inject
   private WareHouseService remoteService;

   public WareHouseCreateService setModel(WareHouse model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
