package org.adorsys.adpharma.client.jpa.rolename;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class RoleNameLoadService extends Service<RoleName>
{

   @Inject
   private RoleNameService remoteService;

   private Long id;

   public RoleNameLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
