package org.adorsys.adpharma.client.jpa.packagingmode;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;

public class PackagingModeEditService extends Service<PackagingMode>
{

   @Inject
   private PackagingModeService remoteService;

   private PackagingMode entity;

   public PackagingModeEditService setPackagingMode(PackagingMode entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
