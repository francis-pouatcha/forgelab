package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesOrderRemoveService extends Service<SalesOrder>
{

   @Inject
   private SalesOrderService remoteService;

   private SalesOrder entity;

   public SalesOrderRemoveService setEntity(SalesOrder entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
