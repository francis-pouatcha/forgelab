package org.adorsys.adpharma.client.jpa.insurrance;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;

public class InsurranceEditService extends Service<Insurrance>
{

   @Inject
   private InsurranceService remoteService;

   private Insurrance entity;

   public InsurranceEditService setInsurrance(Insurrance entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
