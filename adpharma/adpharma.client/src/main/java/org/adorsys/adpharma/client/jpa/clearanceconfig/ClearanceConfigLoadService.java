package org.adorsys.adpharma.client.jpa.clearanceconfig;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ClearanceConfigLoadService extends Service<ClearanceConfig>
{

   @Inject
   private ClearanceConfigService remoteService;

   private Long id;

   public ClearanceConfigLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<ClearanceConfig> createTask()
   {
      return new Task<ClearanceConfig>()
      {
         @Override
         protected ClearanceConfig call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
