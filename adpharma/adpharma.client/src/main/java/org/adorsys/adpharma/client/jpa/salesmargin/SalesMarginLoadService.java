package org.adorsys.adpharma.client.jpa.salesmargin;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesMarginLoadService extends Service<SalesMargin>
{

   @Inject
   private SalesMarginService remoteService;

   private Long id;

   public SalesMarginLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
