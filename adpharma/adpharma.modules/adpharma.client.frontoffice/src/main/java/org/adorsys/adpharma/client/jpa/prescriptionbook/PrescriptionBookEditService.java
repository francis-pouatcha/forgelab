package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PrescriptionBookEditService extends Service<PrescriptionBook>
{

   @Inject
   private PrescriptionBookService remoteService;

   private PrescriptionBook entity;

   public PrescriptionBookEditService setPrescriptionBook(PrescriptionBook entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
