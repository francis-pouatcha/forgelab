package org.adorsys.adpharma.client.jpa.company;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CompanyLoadService extends Service<Company>
{

   @Inject
   private CompanyService remoteService;

   private Long id;

   public CompanyLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<Company> createTask()
   {
      return new Task<Company>()
      {
         @Override
         protected Company call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
