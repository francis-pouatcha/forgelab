package org.adorsys.adpharma.client.access;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PermsTask extends Service<String>
{

   @Inject
   private PermsService remoteService;

   private String loginName;

   public PermsTask setLoginName(String loginName)
   {
      this.loginName = loginName;
      return this;
   }

   @Override
   protected Task<String> createTask()
   {
      return new Task<String>()
      {
         @Override
         protected String call() throws Exception
         {
            if (loginName == null)
               return null;
            return remoteService.loadDCs(loginName);
         }
      };
   }
}
