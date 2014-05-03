package org.adorsys.adpharma.client.jpa.procurementorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProcurementOrderEditService extends Service<ProcurementOrder>
{

   @Inject
   private ProcurementOrderService remoteService;

   private ProcurementOrder entity;

   public ProcurementOrderEditService setProcurementOrder(ProcurementOrder entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<ProcurementOrder> createTask()
   {
      return new Task<ProcurementOrder>()
      {
         @Override
         protected ProcurementOrder call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
