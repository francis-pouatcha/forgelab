package org.adorsys.adpharma.client.jpa.prescriber;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PrescriberLoadService extends Service<Prescriber>
{

   @Inject
   private PrescriberService remoteService;

   private Long id;

   public PrescriberLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
