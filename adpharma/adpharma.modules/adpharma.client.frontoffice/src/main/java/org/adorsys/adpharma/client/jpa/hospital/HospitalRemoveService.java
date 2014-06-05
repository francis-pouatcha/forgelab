package org.adorsys.adpharma.client.jpa.hospital;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class HospitalRemoveService extends Service<Hospital>
{

   @Inject
   private HospitalService remoteService;

   private Hospital entity;

   public HospitalRemoveService setEntity(Hospital entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
