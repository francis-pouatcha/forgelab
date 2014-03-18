package org.adorsys.adpharma.client.jpa.hospital;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class HospitalLoadService extends Service<Hospital>
{

   @Inject
   private HospitalService remoteService;

   private Long id;

   public HospitalLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<Hospital> createTask()
   {
      return new Task<Hospital>()
      {
         @Override
         protected Hospital call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
