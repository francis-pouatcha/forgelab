package org.adorsys.adpharma.client.jpa.rolename;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class RoleNameRemoveService extends Service<RoleName>
{

   @Inject
   private RoleNameService remoteService;

   private RoleName entity;

   public RoleNameRemoveService setEntity(RoleName entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<RoleName> createTask()
   {
      return new Task<RoleName>()
      {
         @Override
         protected RoleName call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
