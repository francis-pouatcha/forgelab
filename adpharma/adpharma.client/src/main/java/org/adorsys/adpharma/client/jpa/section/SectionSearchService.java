package org.adorsys.adpharma.client.jpa.section;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SectionSearchService extends Service<SectionSearchResult>
{

   @Inject
   private SectionService remoteService;

   private SectionSearchInput searchInputs;

   public SectionSearchService setSearchInputs(SectionSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<SectionSearchResult> createTask()
   {
      return new Task<SectionSearchResult>()
      {
         @Override
         protected SectionSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
