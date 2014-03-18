package org.adorsys.adpharma.client.jpa.salesmargin;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesMarginSearchService extends Service<SalesMarginSearchResult>
{

   @Inject
   private SalesMarginService remoteService;

   private SalesMarginSearchInput searchInputs;

   public SalesMarginSearchService setSearchInputs(SalesMarginSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<SalesMarginSearchResult> createTask()
   {
      return new Task<SalesMarginSearchResult>()
      {
         @Override
         protected SalesMarginSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
