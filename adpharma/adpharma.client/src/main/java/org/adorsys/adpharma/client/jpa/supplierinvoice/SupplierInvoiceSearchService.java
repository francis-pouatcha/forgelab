package org.adorsys.adpharma.client.jpa.supplierinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SupplierInvoiceSearchService extends Service<SupplierInvoiceSearchResult>
{

   @Inject
   private SupplierInvoiceService remoteService;

   private SupplierInvoiceSearchInput searchInputs;

   public SupplierInvoiceSearchService setSearchInputs(SupplierInvoiceSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<SupplierInvoiceSearchResult> createTask()
   {
      return new Task<SupplierInvoiceSearchResult>()
      {
         @Override
         protected SupplierInvoiceSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
