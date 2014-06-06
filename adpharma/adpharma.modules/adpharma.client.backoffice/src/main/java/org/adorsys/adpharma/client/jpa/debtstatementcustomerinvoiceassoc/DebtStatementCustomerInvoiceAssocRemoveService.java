package org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementRemoveService;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementService;

public class DebtStatementCustomerInvoiceAssocRemoveService extends Service<DebtStatementCustomerInvoiceAssoc>
{

	   @Inject
	   private DebtStatementCustomerInvoiceAssocService remoteService;

	   private DebtStatementCustomerInvoiceAssoc entity;

	   public DebtStatementCustomerInvoiceAssocRemoveService setEntity(DebtStatementCustomerInvoiceAssoc entity)
	   {
	      this.entity = entity;
	      return this;
	   }

	   @Override
	   protected Task<DebtStatementCustomerInvoiceAssoc> createTask()
	   {
	      return new Task<DebtStatementCustomerInvoiceAssoc>()
	      {
	         @Override
	         protected DebtStatementCustomerInvoiceAssoc call() throws Exception
	         {
	            return remoteService.deleteById(entity.getId());
	         }
	      };
	   }

}
