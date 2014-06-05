package org.adorsys.adpharma.client.jpa.employer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class EmployerRemoveService extends Service<Employer>
{

   @Inject
   private EmployerService remoteService;

   private Employer entity;

   public EmployerRemoveService setEntity(Employer entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
