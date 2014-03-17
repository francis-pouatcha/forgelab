package org.adorsys.adpharma.client.jpa.permissionname;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionName;

public class PermissionNameRemoveService extends Service<PermissionName>
{

   @Inject
   private PermissionNameService remoteService;

   private PermissionName entity;

   public PermissionNameRemoveService setEntity(PermissionName entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
