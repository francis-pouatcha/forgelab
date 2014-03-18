package org.adorsys.adpharma.client.jpa.clearanceconfig;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;

public class ClearanceConfigEditService extends Service<ClearanceConfig>
{

   @Inject
   private ClearanceConfigService remoteService;

   private ClearanceConfig entity;

   public ClearanceConfigEditService setClearanceConfig(ClearanceConfig entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
