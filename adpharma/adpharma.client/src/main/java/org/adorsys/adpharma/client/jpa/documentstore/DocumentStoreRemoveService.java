package org.adorsys.adpharma.client.jpa.documentstore;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DocumentStoreRemoveService extends Service<DocumentStore>
{

   @Inject
   private DocumentStoreService remoteService;

   private DocumentStore entity;

   public DocumentStoreRemoveService setEntity(DocumentStore entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
