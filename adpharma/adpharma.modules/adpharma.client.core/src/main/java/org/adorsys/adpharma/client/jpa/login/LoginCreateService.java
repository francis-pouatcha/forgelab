package org.adorsys.adpharma.client.jpa.login;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoginCreateService extends Service<Login>
{

   private Login model;

   @Inject
   private LoginService remoteService;

   public LoginCreateService setModel(Login model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
