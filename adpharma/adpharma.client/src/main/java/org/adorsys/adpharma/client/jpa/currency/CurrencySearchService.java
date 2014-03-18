package org.adorsys.adpharma.client.jpa.currency;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CurrencySearchService extends Service<CurrencySearchResult>
{

   @Inject
   private CurrencyService remoteService;

   private CurrencySearchInput searchInputs;

   public CurrencySearchService setSearchInputs(CurrencySearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<CurrencySearchResult> createTask()
   {
      return new Task<CurrencySearchResult>()
      {
         @Override
         protected CurrencySearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
