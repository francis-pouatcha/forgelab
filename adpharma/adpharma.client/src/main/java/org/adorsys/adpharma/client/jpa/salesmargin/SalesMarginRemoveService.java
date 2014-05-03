package org.adorsys.adpharma.client.jpa.salesmargin;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesMarginRemoveService extends Service<SalesMargin>
{

   @Inject
   private SalesMarginService remoteService;

   private SalesMargin entity;

   public SalesMarginRemoveService setEntity(SalesMargin entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<SalesMargin> createTask()
   {
      return new Task<SalesMargin>()
      {
         @Override
         protected SalesMargin call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
