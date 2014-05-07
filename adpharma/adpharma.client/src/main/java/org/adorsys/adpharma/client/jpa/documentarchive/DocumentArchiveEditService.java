package org.adorsys.adpharma.client.jpa.documentarchive;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DocumentArchiveEditService extends Service<DocumentArchive>
{

   @Inject
   private DocumentArchiveService remoteService;

   private DocumentArchive entity;

   public DocumentArchiveEditService setDocumentArchive(DocumentArchive entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
