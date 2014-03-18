package org.adorsys.adpharma.client.jpa.company;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.company.Company;

public class CompanyRemoveService extends Service<Company>
{

   @Inject
   private CompanyService remoteService;

   private Company entity;

   public CompanyRemoveService setEntity(Company entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
