package org.adorsys.adpharma.client.jpa.company;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CompanySearchService extends Service<CompanySearchResult>
{

   @Inject
   private CompanyService remoteService;

   private CompanySearchInput searchInputs;

   public CompanySearchService setSearchInputs(CompanySearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<CompanySearchResult> createTask()
   {
      return new Task<CompanySearchResult>()
      {
         @Override
         protected CompanySearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
