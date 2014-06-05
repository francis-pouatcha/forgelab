package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SalesOrderCreateService extends Service<SalesOrder>
{

   private SalesOrder model;

   @Inject
   private SalesOrderService remoteService;

   public SalesOrderCreateService setModel(SalesOrder model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
