package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PrescriptionBookRemoveService extends Service<PrescriptionBook>
{

   @Inject
   private PrescriptionBookService remoteService;

   private PrescriptionBook entity;

   public PrescriptionBookRemoveService setEntity(PrescriptionBook entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
