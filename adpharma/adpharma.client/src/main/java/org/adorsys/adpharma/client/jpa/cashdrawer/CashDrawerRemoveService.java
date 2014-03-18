package org.adorsys.adpharma.client.jpa.cashdrawer;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class CashDrawerRemoveService extends Service<CashDrawer>
{

   @Inject
   private CashDrawerService remoteService;

   private CashDrawer entity;

   public CashDrawerRemoveService setEntity(CashDrawer entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
