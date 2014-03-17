package org.adorsys.adpharma.client.jpa.packagingmode;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PackagingModeSearchService extends Service<PackagingModeSearchResult>
{

   @Inject
   private PackagingModeService remoteService;

   private PackagingModeSearchInput searchInputs;

   public PackagingModeSearchService setSearchInputs(PackagingModeSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<PackagingModeSearchResult> createTask()
   {
      return new Task<PackagingModeSearchResult>()
      {
         @Override
         protected PackagingModeSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
