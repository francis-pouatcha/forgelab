package org.adorsys.adpharma.client.jpa.debtstatement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DebtStatementLoadService extends Service<DebtStatement>
{

   @Inject
   private DebtStatementService remoteService;

   private Long id;

   public DebtStatementLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
