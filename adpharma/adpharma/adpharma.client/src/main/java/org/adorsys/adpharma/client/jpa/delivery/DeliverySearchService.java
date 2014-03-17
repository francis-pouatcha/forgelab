package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DeliverySearchService extends Service<DeliverySearchResult>
{

   @Inject
   private DeliveryService remoteService;

   private DeliverySearchInput searchInputs;

   public DeliverySearchService setSearchInputs(DeliverySearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<DeliverySearchResult> createTask()
   {
      return new Task<DeliverySearchResult>()
      {
         @Override
         protected DeliverySearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
