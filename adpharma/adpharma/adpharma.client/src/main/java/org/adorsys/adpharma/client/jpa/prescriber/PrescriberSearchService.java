package org.adorsys.adpharma.client.jpa.prescriber;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PrescriberSearchService extends Service<PrescriberSearchResult>
{

   @Inject
   private PrescriberService remoteService;

   private PrescriberSearchInput searchInputs;

   public PrescriberSearchService setSearchInputs(PrescriberSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<PrescriberSearchResult> createTask()
   {
      return new Task<PrescriberSearchResult>()
      {
         @Override
         protected PrescriberSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
