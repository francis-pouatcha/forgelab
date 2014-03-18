package org.adorsys.adpharma.client.jpa.agency;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.agency.Agency;

public class AgencyRemoveService extends Service<Agency>
{

   @Inject
   private AgencyService remoteService;

   private Agency entity;

   public AgencyRemoveService setEntity(Agency entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
