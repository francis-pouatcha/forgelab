package org.adorsys.adpharma.client.jpa.documentstore;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DocumentStoreSearchService extends Service<DocumentStoreSearchResult>
{

   @Inject
   private DocumentStoreService remoteService;

   private DocumentStoreSearchInput searchInputs;

   public DocumentStoreSearchService setSearchInputs(DocumentStoreSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<DocumentStoreSearchResult> createTask()
   {
      return new Task<DocumentStoreSearchResult>()
      {
         @Override
         protected DocumentStoreSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
