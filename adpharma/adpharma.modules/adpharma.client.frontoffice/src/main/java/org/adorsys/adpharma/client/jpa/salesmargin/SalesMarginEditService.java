package org.adorsys.adpharma.client.jpa.salesmargin;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesMarginEditService extends Service<SalesMargin>
{

   @Inject
   private SalesMarginService remoteService;

   private SalesMargin entity;

   public SalesMarginEditService setSalesMargin(SalesMargin entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
