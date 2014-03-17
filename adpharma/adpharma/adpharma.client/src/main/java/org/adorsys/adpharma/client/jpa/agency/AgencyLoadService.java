package org.adorsys.adpharma.client.jpa.agency;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class AgencyLoadService extends Service<Agency>
{

   @Inject
   private AgencyService remoteService;

   private Long id;

   public AgencyLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
