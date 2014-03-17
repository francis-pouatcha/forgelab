package org.adorsys.adpharma.client.jpa.clearanceconfig;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;

public class ClearanceConfigRemoveService extends Service<ClearanceConfig>
{

   @Inject
   private ClearanceConfigService remoteService;

   private ClearanceConfig entity;

   public ClearanceConfigRemoveService setEntity(ClearanceConfig entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
