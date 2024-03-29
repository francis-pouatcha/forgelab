package org.adorsys.adpharma.client.jpa.insurrance;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InsurranceCreateService extends Service<Insurrance>
{

   private Insurrance model;

   @Inject
   private InsurranceService remoteService;

   public InsurranceCreateService setModel(Insurrance model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<Insurrance> createTask()
   {
      return new Task<Insurrance>()
      {
         @Override
         protected Insurrance call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
