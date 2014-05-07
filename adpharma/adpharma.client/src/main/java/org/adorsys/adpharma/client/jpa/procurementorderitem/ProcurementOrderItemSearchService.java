package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProcurementOrderItemSearchService extends Service<ProcurementOrderItemSearchResult>
{

   @Inject
   private ProcurementOrderItemService remoteService;

   private ProcurementOrderItemSearchInput searchInputs;

   public ProcurementOrderItemSearchService setSearchInputs(ProcurementOrderItemSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<ProcurementOrderItemSearchResult> createTask()
   {
      return new Task<ProcurementOrderItemSearchResult>()
      {
         @Override
         protected ProcurementOrderItemSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
