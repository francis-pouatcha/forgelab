package org.adorsys.adpharma.client.jpa.clearanceconfig;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;

@Singleton
public class ClearanceConfigCreateService extends Service<ClearanceConfig>
{

   private ClearanceConfig model;

   @Inject
   private ClearanceConfigService remoteService;

   public ClearanceConfigCreateService setModel(ClearanceConfig model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
