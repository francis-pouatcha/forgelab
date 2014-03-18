package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PrescriptionBookLoadService extends Service<PrescriptionBook>
{

   @Inject
   private PrescriptionBookService remoteService;

   private Long id;

   public PrescriptionBookLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<PrescriptionBook> createTask()
   {
      return new Task<PrescriptionBook>()
      {
         @Override
         protected PrescriptionBook call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
