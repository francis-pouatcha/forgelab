package org.adorsys.adpharma.client.jpa.salesorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;

public class SalesOrderItemRemoveService extends Service<SalesOrderItem>
{

   @Inject
   private SalesOrderItemService remoteService;

   private SalesOrderItem entity;

   public SalesOrderItemRemoveService setEntity(SalesOrderItem entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
