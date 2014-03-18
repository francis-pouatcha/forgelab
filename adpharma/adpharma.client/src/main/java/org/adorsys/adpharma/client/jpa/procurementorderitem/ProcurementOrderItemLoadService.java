package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProcurementOrderItemLoadService extends Service<ProcurementOrderItem>
{

   @Inject
   private ProcurementOrderItemService remoteService;

   private Long id;

   public ProcurementOrderItemLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
