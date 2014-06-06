package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesOrderLoadService extends Service<SalesOrder>
{

   @Inject
   private SalesOrderService remoteService;

   private Long id;

   public SalesOrderLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<SalesOrder> createTask()
   {
      return new Task<SalesOrder>()
      {
         @Override
         protected SalesOrder call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
