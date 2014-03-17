package org.adorsys.adpharma.client.jpa.hospital;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.hospital.Hospital;

@Singleton
public class HospitalCreateService extends Service<Hospital>
{

   private Hospital model;

   @Inject
   private HospitalService remoteService;

   public HospitalCreateService setModel(Hospital model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
