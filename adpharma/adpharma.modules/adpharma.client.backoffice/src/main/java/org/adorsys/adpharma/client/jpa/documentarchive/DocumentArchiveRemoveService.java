package org.adorsys.adpharma.client.jpa.documentarchive;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DocumentArchiveRemoveService extends Service<DocumentArchive>
{

   @Inject
   private DocumentArchiveService remoteService;

   private DocumentArchive entity;

   public DocumentArchiveRemoveService setEntity(DocumentArchive entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<DocumentArchive> createTask()
   {
      return new Task<DocumentArchive>()
      {
         @Override
         protected DocumentArchive call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
