package org.adorsys.adpharma.client.jpa.packagingmode;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;

public class PackagingModeRemoveService extends Service<PackagingMode>
{

   @Inject
   private PackagingModeService remoteService;

   private PackagingMode entity;

   public PackagingModeRemoveService setEntity(PackagingMode entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
