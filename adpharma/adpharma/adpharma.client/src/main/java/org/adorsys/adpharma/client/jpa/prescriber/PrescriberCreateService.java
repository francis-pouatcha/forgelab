package org.adorsys.adpharma.client.jpa.prescriber;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;

@Singleton
public class PrescriberCreateService extends Service<Prescriber>
{

   private Prescriber model;

   @Inject
   private PrescriberService remoteService;

   public PrescriberCreateService setModel(Prescriber model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
