package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProcurementOrderSearchService extends Service<ProcurementOrderSearchResult>
{

   @Inject
   private ProcurementOrderService remoteService;

   private ProcurementOrderSearchInput searchInputs;

   public ProcurementOrderSearchService setSearchInputs(ProcurementOrderSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<ProcurementOrderSearchResult> createTask()
   {
      return new Task<ProcurementOrderSearchResult>()
      {
         @Override
         protected ProcurementOrderSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
