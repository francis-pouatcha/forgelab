package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WareHouseArticleLotCreateService extends Service<WareHouseArticleLot>
{

   private WareHouseArticleLot model;

   @Inject
   private WareHouseArticleLotService remoteService;

   public WareHouseArticleLotCreateService setModel(WareHouseArticleLot model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
