package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProcurementOrderItemEditService extends Service<ProcurementOrderItem>
{

   @Inject
   private ProcurementOrderItemService remoteService;

   private ProcurementOrderItem entity;

   public ProcurementOrderItemEditService setProcurementOrderItem(ProcurementOrderItem entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
