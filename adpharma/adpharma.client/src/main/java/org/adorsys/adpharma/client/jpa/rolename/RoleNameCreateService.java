package org.adorsys.adpharma.client.jpa.rolename;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.rolename.RoleName;

@Singleton
public class RoleNameCreateService extends Service<RoleName>
{

   private RoleName model;

   @Inject
   private RoleNameService remoteService;

   public RoleNameCreateService setModel(RoleName model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
