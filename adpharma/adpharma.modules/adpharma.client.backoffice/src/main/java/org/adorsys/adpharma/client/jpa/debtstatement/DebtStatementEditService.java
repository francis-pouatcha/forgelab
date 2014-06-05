package org.adorsys.adpharma.client.jpa.debtstatement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DebtStatementEditService extends Service<DebtStatement>
{

   @Inject
   private DebtStatementService remoteService;

   private DebtStatement entity;

   public DebtStatementEditService setDebtStatement(DebtStatement entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<DebtStatement> createTask()
   {
      return new Task<DebtStatement>()
      {
         @Override
         protected DebtStatement call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
