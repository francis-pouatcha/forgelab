package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CashDrawerSearchService extends Service<CashDrawerSearchResult>
{

   @Inject
   private CashDrawerService remoteService;

   private CashDrawerSearchInput searchInputs;

   public CashDrawerSearchService setSearchInputs(CashDrawerSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<CashDrawerSearchResult> createTask()
   {
      return new Task<CashDrawerSearchResult>()
      {
         @Override
         protected CashDrawerSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
