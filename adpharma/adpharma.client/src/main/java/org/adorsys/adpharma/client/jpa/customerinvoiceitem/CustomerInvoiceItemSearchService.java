package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerInvoiceItemSearchService extends Service<CustomerInvoiceItemSearchResult>
{

   @Inject
   private CustomerInvoiceItemService remoteService;

   private CustomerInvoiceItemSearchInput searchInputs;

   public CustomerInvoiceItemSearchService setSearchInputs(CustomerInvoiceItemSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<CustomerInvoiceItemSearchResult> createTask()
   {
      return new Task<CustomerInvoiceItemSearchResult>()
      {
         @Override
         protected CustomerInvoiceItemSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
