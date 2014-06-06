package org.adorsys.adpharma.client.jpa.documentarchive;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DocumentArchiveLoadService extends Service<DocumentArchive>
{

   @Inject
   private DocumentArchiveService remoteService;

   private Long id;

   public DocumentArchiveLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
