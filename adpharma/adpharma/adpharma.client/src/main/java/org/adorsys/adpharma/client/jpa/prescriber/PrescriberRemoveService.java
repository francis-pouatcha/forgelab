package org.adorsys.adpharma.client.jpa.prescriber;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;

public class PrescriberRemoveService extends Service<Prescriber>
{

   @Inject
   private PrescriberService remoteService;

   private Prescriber entity;

   public PrescriberRemoveService setEntity(Prescriber entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
