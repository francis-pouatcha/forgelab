package org.adorsys.adpharma.client.jpa.hospital;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.hospital.Hospital;

public class HospitalEditService extends Service<Hospital>
{

   @Inject
   private HospitalService remoteService;

   private Hospital entity;

   public HospitalEditService setHospital(Hospital entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
