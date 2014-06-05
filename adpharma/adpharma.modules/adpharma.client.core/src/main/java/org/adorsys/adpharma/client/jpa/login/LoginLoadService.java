package org.adorsys.adpharma.client.jpa.login;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class LoginLoadService extends Service<Login>
{

   @Inject
   private LoginService remoteService;

   private Long id;

   public LoginLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
