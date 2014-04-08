package org.adorsys.adpharma.client.jpa.documentarchive;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.documentarchive.DocumentArchive;

@Singleton
public class DocumentArchiveCreateService extends Service<DocumentArchive>
{

   private DocumentArchive model;

   @Inject
   private DocumentArchiveService remoteService;

   public DocumentArchiveCreateService setModel(DocumentArchive model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
