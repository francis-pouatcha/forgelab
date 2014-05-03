package org.adorsys.adpharma.client.jpa.documentstore;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DocumentStoreCreateService extends Service<DocumentStore>
{

   private DocumentStore model;

   @Inject
   private DocumentStoreService remoteService;

   public DocumentStoreCreateService setModel(DocumentStore model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
