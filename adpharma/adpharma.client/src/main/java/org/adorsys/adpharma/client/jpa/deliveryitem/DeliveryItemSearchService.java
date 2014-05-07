package org.adorsys.adpharma.client.jpa.deliveryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DeliveryItemSearchService extends Service<DeliveryItemSearchResult>
{

   @Inject
   private DeliveryItemService remoteService;

   private DeliveryItemSearchInput searchInputs;

   public DeliveryItemSearchService setSearchInputs(DeliveryItemSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<DeliveryItemSearchResult> createTask()
   {
      return new Task<DeliveryItemSearchResult>()
      {
         @Override
         protected DeliveryItemSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
