package org.adorsys.adpharma.client.jpa.login;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class LoginEditService extends Service<Login>
{

   @Inject
   private LoginService remoteService;

   private Login entity;

   public LoginEditService setLogin(Login entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<Login> createTask()
   {
      return new Task<Login>()
      {
         @Override
         protected Login call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
