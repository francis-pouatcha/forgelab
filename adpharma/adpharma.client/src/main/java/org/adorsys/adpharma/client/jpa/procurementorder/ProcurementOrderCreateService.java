package org.adorsys.adpharma.client.jpa.procurementorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

@Singleton
public class ProcurementOrderCreateService extends Service<ProcurementOrder>
{

   private ProcurementOrder model;

   @Inject
   private ProcurementOrderService remoteService;

   public ProcurementOrderCreateService setModel(ProcurementOrder model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
