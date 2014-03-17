package org.adorsys.adpharma.client.jpa.employer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.employer.Employer;

public class EmployerEditService extends Service<Employer>
{

   @Inject
   private EmployerService remoteService;

   private Employer entity;

   public EmployerEditService setEmployer(Employer entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
