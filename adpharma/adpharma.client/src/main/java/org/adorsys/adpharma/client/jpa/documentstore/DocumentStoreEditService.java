package org.adorsys.adpharma.client.jpa.documentstore;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.documentstore.DocumentStore;

public class DocumentStoreEditService extends Service<DocumentStore>
{

   @Inject
   private DocumentStoreService remoteService;

   private DocumentStore entity;

   public DocumentStoreEditService setDocumentStore(DocumentStore entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
