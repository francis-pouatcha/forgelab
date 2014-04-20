package org.adorsys.adpharma.client.jpa.warehouse;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class WareHouseSearchService extends Service<WareHouseSearchResult>
{

   @Inject
   private WareHouseService remoteService;

   private WareHouseSearchInput searchInputs;

   public WareHouseSearchService setSearchInputs(WareHouseSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<WareHouseSearchResult> createTask()
   {
      return new Task<WareHouseSearchResult>()
      {
         @Override
         protected WareHouseSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
