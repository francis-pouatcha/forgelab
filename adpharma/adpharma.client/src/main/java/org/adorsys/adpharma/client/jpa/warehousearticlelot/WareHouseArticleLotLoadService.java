package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class WareHouseArticleLotLoadService extends Service<WareHouseArticleLot>
{

   @Inject
   private WareHouseArticleLotService remoteService;

   private Long id;

   public WareHouseArticleLotLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
