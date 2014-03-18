package org.adorsys.adpharma.client.jpa.debtstatement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;

@Singleton
public class DebtStatementCreateService extends Service<DebtStatement>
{

   private DebtStatement model;

   @Inject
   private DebtStatementService remoteService;

   public DebtStatementCreateService setModel(DebtStatement model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
