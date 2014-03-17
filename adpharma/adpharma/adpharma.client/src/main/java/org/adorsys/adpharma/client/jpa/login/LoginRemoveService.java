package org.adorsys.adpharma.client.jpa.login;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.login.Login;

public class LoginRemoveService extends Service<Login>
{

   @Inject
   private LoginService remoteService;

   private Login entity;

   public LoginRemoveService setEntity(Login entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
