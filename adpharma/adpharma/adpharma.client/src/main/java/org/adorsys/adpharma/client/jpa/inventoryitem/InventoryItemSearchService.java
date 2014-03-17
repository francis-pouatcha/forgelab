package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InventoryItemSearchService extends Service<InventoryItemSearchResult>
{

   @Inject
   private InventoryItemService remoteService;

   private InventoryItemSearchInput searchInputs;

   public InventoryItemSearchService setSearchInputs(InventoryItemSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<InventoryItemSearchResult> createTask()
   {
      return new Task<InventoryItemSearchResult>()
      {
         @Override
         protected InventoryItemSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
