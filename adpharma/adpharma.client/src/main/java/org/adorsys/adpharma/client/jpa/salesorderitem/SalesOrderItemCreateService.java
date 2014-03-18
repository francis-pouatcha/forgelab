package org.adorsys.adpharma.client.jpa.salesorderitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;

@Singleton
public class SalesOrderItemCreateService extends Service<SalesOrderItem>
{

   private SalesOrderItem model;

   @Inject
   private SalesOrderItemService remoteService;

   public SalesOrderItemCreateService setModel(SalesOrderItem model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
