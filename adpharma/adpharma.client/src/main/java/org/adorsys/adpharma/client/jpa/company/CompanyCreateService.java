package org.adorsys.adpharma.client.jpa.company;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.company.Company;

@Singleton
public class CompanyCreateService extends Service<Company>
{

   private Company model;

   @Inject
   private CompanyService remoteService;

   public CompanyCreateService setModel(Company model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
