package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;

public class ProcurementOrderItemRemoveService extends Service<ProcurementOrderItem>
{

   @Inject
   private ProcurementOrderItemService remoteService;

   private ProcurementOrderItem entity;

   public ProcurementOrderItemRemoveService setEntity(ProcurementOrderItem entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
