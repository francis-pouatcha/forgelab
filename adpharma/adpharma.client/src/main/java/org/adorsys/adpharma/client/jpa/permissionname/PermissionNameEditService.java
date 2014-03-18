package org.adorsys.adpharma.client.jpa.permissionname;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionName;

public class PermissionNameEditService extends Service<PermissionName>
{

   @Inject
   private PermissionNameService remoteService;

   private PermissionName entity;

   public PermissionNameEditService setPermissionName(PermissionName entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
