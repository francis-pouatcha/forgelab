package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProcurementOrderItemCreateService extends Service<ProcurementOrderItem>
{

   private ProcurementOrderItem model;

   @Inject
   private ProcurementOrderItemService remoteService;

   public ProcurementOrderItemCreateService setModel(ProcurementOrderItem model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<ProcurementOrderItem> createTask()
   {
      return new Task<ProcurementOrderItem>()
      {
         @Override
         protected ProcurementOrderItem call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
