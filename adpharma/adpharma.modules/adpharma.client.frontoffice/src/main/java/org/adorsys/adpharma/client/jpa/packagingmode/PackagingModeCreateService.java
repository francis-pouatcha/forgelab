package org.adorsys.adpharma.client.jpa.packagingmode;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PackagingModeCreateService extends Service<PackagingMode>
{

   private PackagingMode model;

   @Inject
   private PackagingModeService remoteService;

   public PackagingModeCreateService setModel(PackagingMode model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<PackagingMode> createTask()
   {
      return new Task<PackagingMode>()
      {
         @Override
         protected PackagingMode call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
