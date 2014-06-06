package org.adorsys.adpharma.client.jpa.documentarchive;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DocumentArchiveSearchService extends Service<DocumentArchiveSearchResult>
{

   @Inject
   private DocumentArchiveService remoteService;

   private DocumentArchiveSearchInput searchInputs;

   public DocumentArchiveSearchService setSearchInputs(DocumentArchiveSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<DocumentArchiveSearchResult> createTask()
   {
      return new Task<DocumentArchiveSearchResult>()
      {
         @Override
         protected DocumentArchiveSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
