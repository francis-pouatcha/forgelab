package org.adorsys.adpharma.client.jpa.insurrance;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class InsurranceLoadService extends Service<Insurrance>
{

   @Inject
   private InsurranceService remoteService;

   private Long id;

   public InsurranceLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
