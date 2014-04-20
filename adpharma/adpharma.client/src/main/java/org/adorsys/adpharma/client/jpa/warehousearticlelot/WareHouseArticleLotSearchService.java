package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class WareHouseArticleLotSearchService extends Service<WareHouseArticleLotSearchResult>
{

   @Inject
   private WareHouseArticleLotService remoteService;

   private WareHouseArticleLotSearchInput searchInputs;

   public WareHouseArticleLotSearchService setSearchInputs(WareHouseArticleLotSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<WareHouseArticleLotSearchResult> createTask()
   {
      return new Task<WareHouseArticleLotSearchResult>()
      {
         @Override
         protected WareHouseArticleLotSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
