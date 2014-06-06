package org.adorsys.adpharma.client.jpa.prescriber;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PrescriberEditService extends Service<Prescriber>
{

   @Inject
   private PrescriberService remoteService;

   private Prescriber entity;

   public PrescriberEditService setPrescriber(Prescriber entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<Prescriber> createTask()
   {
      return new Task<Prescriber>()
      {
         @Override
         protected Prescriber call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
