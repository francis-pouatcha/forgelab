package org.adorsys.adpharma.client.jpa.vat;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class VATSearchService extends Service<VATSearchResult>
{

   @Inject
   private VATService remoteService;

   private VATSearchInput searchInputs;

   public VATSearchService setSearchInputs(VATSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<VATSearchResult> createTask()
   {
      return new Task<VATSearchResult>()
      {
         @Override
         protected VATSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
