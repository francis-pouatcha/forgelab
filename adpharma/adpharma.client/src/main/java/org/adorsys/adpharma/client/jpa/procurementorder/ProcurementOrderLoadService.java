package org.adorsys.adpharma.client.jpa.procurementorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProcurementOrderLoadService extends Service<ProcurementOrder>
{

   @Inject
   private ProcurementOrderService remoteService;

   private Long id;

   public ProcurementOrderLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
