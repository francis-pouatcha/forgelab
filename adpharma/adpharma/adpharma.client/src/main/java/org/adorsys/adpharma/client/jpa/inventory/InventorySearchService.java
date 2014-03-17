package org.adorsys.adpharma.client.jpa.inventory;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InventorySearchService extends Service<InventorySearchResult>
{

   @Inject
   private InventoryService remoteService;

   private InventorySearchInput searchInputs;

   public InventorySearchService setSearchInputs(InventorySearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<InventorySearchResult> createTask()
   {
      return new Task<InventorySearchResult>()
      {
         @Override
         protected InventorySearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
