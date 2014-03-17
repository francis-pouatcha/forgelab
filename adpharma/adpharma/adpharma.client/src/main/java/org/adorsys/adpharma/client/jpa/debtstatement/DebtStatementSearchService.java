package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DebtStatementSearchService extends Service<DebtStatementSearchResult>
{

   @Inject
   private DebtStatementService remoteService;

   private DebtStatementSearchInput searchInputs;

   public DebtStatementSearchService setSearchInputs(DebtStatementSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<DebtStatementSearchResult> createTask()
   {
      return new Task<DebtStatementSearchResult>()
      {
         @Override
         protected DebtStatementSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
