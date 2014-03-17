package org.adorsys.adpharma.client.jpa.supplier;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SupplierSearchService extends Service<SupplierSearchResult>
{

   @Inject
   private SupplierService remoteService;

   private SupplierSearchInput searchInputs;

   public SupplierSearchService setSearchInputs(SupplierSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<SupplierSearchResult> createTask()
   {
      return new Task<SupplierSearchResult>()
      {
         @Override
         protected SupplierSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
