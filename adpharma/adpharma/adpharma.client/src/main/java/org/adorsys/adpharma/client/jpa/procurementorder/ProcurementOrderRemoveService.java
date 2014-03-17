package org.adorsys.adpharma.client.jpa.procurementorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

public class ProcurementOrderRemoveService extends Service<ProcurementOrder>
{

   @Inject
   private ProcurementOrderService remoteService;

   private ProcurementOrder entity;

   public ProcurementOrderRemoveService setEntity(ProcurementOrder entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
