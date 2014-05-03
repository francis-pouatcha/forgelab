package org.adorsys.adpharma.client.jpa.employer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmployerCreateService extends Service<Employer>
{

   private Employer model;

   @Inject
   private EmployerService remoteService;

   public EmployerCreateService setModel(Employer model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
