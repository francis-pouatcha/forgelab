package org.adorsys.adpharma.client.jpa.cashdrawer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CashDrawerLoadService extends Service<CashDrawer>
{

   @Inject
   private CashDrawerService remoteService;

   private Long id;

   public CashDrawerLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<CashDrawer> createTask()
   {
      return new Task<CashDrawer>()
      {
         @Override
         protected CashDrawer call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
