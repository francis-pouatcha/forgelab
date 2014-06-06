package org.adorsys.adpharma.client.jpa.permissionname;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PermissionNameCreateService extends Service<PermissionName>
{

   private PermissionName model;

   @Inject
   private PermissionNameService remoteService;

   public PermissionNameCreateService setModel(PermissionName model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
