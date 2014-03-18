package org.adorsys.adpharma.client.jpa.employer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class EmployerLoadService extends Service<Employer>
{

   @Inject
   private EmployerService remoteService;

   private Long id;

   public EmployerLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<Employer> createTask()
   {
      return new Task<Employer>()
      {
         @Override
         protected Employer call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
