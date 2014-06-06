package org.adorsys.adpharma.client.jpa.packagingmode;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PackagingModeLoadService extends Service<PackagingMode>
{

   @Inject
   private PackagingModeService remoteService;

   private Long id;

   public PackagingModeLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
