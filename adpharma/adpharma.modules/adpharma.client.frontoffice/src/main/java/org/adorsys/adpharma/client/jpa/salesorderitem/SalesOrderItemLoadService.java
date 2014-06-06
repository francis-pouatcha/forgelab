package org.adorsys.adpharma.client.jpa.salesorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesOrderItemLoadService extends Service<SalesOrderItem>
{

   @Inject
   private SalesOrderItemService remoteService;

   private Long id;

   public SalesOrderItemLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<SalesOrderItem> createTask()
   {
      return new Task<SalesOrderItem>()
      {
         @Override
         protected SalesOrderItem call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
