package org.adorsys.adpharma.client.jpa.permissionname;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PermissionNameLoadService extends Service<PermissionName>
{

   @Inject
   private PermissionNameService remoteService;

   private Long id;

   public PermissionNameLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<PermissionName> createTask()
   {
      return new Task<PermissionName>()
      {
         @Override
         protected PermissionName call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
