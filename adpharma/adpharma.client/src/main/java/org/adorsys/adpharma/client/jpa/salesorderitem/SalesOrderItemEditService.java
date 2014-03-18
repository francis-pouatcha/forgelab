package org.adorsys.adpharma.client.jpa.salesorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;

public class SalesOrderItemEditService extends Service<SalesOrderItem>
{

   @Inject
   private SalesOrderItemService remoteService;

   private SalesOrderItem entity;

   public SalesOrderItemEditService setSalesOrderItem(SalesOrderItem entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
