package org.adorsys.adpharma.client.jpa.documentstore;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DocumentStoreLoadService extends Service<DocumentStore>
{

   @Inject
   private DocumentStoreService remoteService;

   private Long id;

   public DocumentStoreLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<DocumentStore> createTask()
   {
      return new Task<DocumentStore>()
      {
         @Override
         protected DocumentStore call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
