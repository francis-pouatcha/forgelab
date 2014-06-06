package org.adorsys.adpharma.client.jpa.employer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class EmployerSearchService extends Service<EmployerSearchResult>
{

   @Inject
   private EmployerService remoteService;

   private EmployerSearchInput searchInputs;

   public EmployerSearchService setSearchInputs(EmployerSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<EmployerSearchResult> createTask()
   {
      return new Task<EmployerSearchResult>()
      {
         @Override
         protected EmployerSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
