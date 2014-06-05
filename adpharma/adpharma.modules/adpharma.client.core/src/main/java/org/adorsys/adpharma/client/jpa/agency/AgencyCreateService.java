package org.adorsys.adpharma.client.jpa.agency;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.agency.Agency;

@Singleton
public class AgencyCreateService extends Service<Agency>
{

   private Agency model;

   @Inject
   private AgencyService remoteService;

   public AgencyCreateService setModel(Agency model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
