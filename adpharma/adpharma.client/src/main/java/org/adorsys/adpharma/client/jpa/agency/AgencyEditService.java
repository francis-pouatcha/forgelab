package org.adorsys.adpharma.client.jpa.agency;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.agency.Agency;

public class AgencyEditService extends Service<Agency>
{

   @Inject
   private AgencyService remoteService;

   private Agency entity;

   public AgencyEditService setAgency(Agency entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<Agency> createTask()
   {
      return new Task<Agency>()
      {
         @Override
         protected Agency call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
