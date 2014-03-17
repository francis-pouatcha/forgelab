package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InsurranceSearchService extends Service<InsurranceSearchResult>
{

   @Inject
   private InsurranceService remoteService;

   private InsurranceSearchInput searchInputs;

   public InsurranceSearchService setSearchInputs(InsurranceSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<InsurranceSearchResult> createTask()
   {
      return new Task<InsurranceSearchResult>()
      {
         @Override
         protected InsurranceSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
