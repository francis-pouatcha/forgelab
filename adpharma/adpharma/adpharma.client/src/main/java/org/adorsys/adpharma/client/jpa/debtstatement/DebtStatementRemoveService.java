package org.adorsys.adpharma.client.jpa.debtstatement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;

public class DebtStatementRemoveService extends Service<DebtStatement>
{

   @Inject
   private DebtStatementService remoteService;

   private DebtStatement entity;

   public DebtStatementRemoveService setEntity(DebtStatement entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
