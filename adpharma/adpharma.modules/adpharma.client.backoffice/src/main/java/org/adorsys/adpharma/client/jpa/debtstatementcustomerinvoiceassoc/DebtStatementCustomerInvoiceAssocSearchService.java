package org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementSearchInput;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementSearchResult;

public class DebtStatementCustomerInvoiceAssocSearchService extends Service<DebtStatementCustomerInvoiceAssocSearchResult>
{

	   @Inject
	   private DebtStatementCustomerInvoiceAssocService remoteService;

	   private DebtStatementCustomerInvoiceAssocSearchInput searchInputs;

	   public DebtStatementCustomerInvoiceAssocSearchService setSearchInputs(DebtStatementCustomerInvoiceAssocSearchInput searchInputs)
	   {
	      this.searchInputs = searchInputs;
	      return this;
	   }

	   @Override
	   protected Task<DebtStatementCustomerInvoiceAssocSearchResult> createTask()
	   {
	      return new Task<DebtStatementCustomerInvoiceAssocSearchResult>()
	      {
	         @Override
	         protected DebtStatementCustomerInvoiceAssocSearchResult call() throws Exception
	         {
	            if (searchInputs == null)
	               return null;
	            return remoteService.findByLike(searchInputs);
	         }
	      };
	   }

}
