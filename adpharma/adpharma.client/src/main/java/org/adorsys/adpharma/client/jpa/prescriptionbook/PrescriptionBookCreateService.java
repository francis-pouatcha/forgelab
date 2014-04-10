package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

public class PrescriptionBookCreateService extends Service<PrescriptionBook>
{

   private PrescriptionBook model;

   @Inject
   private PrescriptionBookService remoteService;

   public PrescriptionBookCreateService setModel(PrescriptionBook model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
