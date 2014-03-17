package org.adorsys.adpharma.client.jpa.cashdrawer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

@Singleton
public class CashDrawerCreateService extends Service<CashDrawer>
{

   private CashDrawer model;

   @Inject
   private CashDrawerService remoteService;

   public CashDrawerCreateService setModel(CashDrawer model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
