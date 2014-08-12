package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;

public class CustomerInvoiceSearchService extends Service<CustomerInvoiceSearchResult>
{

   @Inject
   private CustomerInvoiceService remoteService;

   private CustomerInvoiceSearchInput searchInputs;
   
   private DebtStatement source;

   public CustomerInvoiceSearchService setSearchInputs(CustomerInvoiceSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }
   
   public CustomerInvoiceSearchService setDebtStatement(DebtStatement source)
   {
      this.source = source;
      return this;
   }

   @Override
   protected Task<CustomerInvoiceSearchResult> createTask()
   {
      return new Task<CustomerInvoiceSearchResult>()
      {
         @Override
         protected CustomerInvoiceSearchResult call() throws Exception
         {
            if (searchInputs != null){
            	return remoteService.findByLike(searchInputs);
            }
            
            if (source != null){
            	return remoteService.findCustomerInvoiceBySource(source);
            }
               return null;
         }
      };
   }
}
