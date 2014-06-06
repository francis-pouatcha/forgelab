package org.adorsys.adpharma.client.jpa.company;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.company.Company;

public class CompanyEditService extends Service<Company>
{

   @Inject
   private CompanyService remoteService;

   private Company entity;

   public CompanyEditService setCompany(Company entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
