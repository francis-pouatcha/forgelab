package org.adorsys.adpharma.client.jpa.salesmargin;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SalesMarginCreateService extends Service<SalesMargin>
{

   private SalesMargin model;

   @Inject
   private SalesMarginService remoteService;

   public SalesMarginCreateService setModel(SalesMargin model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
