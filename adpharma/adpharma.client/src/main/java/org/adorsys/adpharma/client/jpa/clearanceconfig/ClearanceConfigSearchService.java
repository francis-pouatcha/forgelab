package org.adorsys.adpharma.client.jpa.clearanceconfig;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ClearanceConfigSearchService extends Service<ClearanceConfigSearchResult>
{

   @Inject
   private ClearanceConfigService remoteService;

   private ClearanceConfigSearchInput searchInputs;

   public ClearanceConfigSearchService setSearchInputs(ClearanceConfigSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<ClearanceConfigSearchResult> createTask()
   {
      return new Task<ClearanceConfigSearchResult>()
      {
         @Override
         protected ClearanceConfigSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
