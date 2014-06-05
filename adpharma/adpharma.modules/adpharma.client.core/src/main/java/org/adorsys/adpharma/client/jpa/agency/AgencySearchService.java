package org.adorsys.adpharma.client.jpa.agency;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class AgencySearchService extends Service<AgencySearchResult>
{

   @Inject
   private AgencyService remoteService;

   private AgencySearchInput searchInputs;

   public AgencySearchService setSearchInputs(AgencySearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<AgencySearchResult> createTask()
   {
      return new Task<AgencySearchResult>()
      {
         @Override
         protected AgencySearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
