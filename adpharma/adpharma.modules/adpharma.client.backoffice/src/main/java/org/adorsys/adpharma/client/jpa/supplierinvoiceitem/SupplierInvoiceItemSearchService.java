package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SupplierInvoiceItemSearchService extends Service<SupplierInvoiceItemSearchResult>
{

   @Inject
   private SupplierInvoiceItemService remoteService;

   private SupplierInvoiceItemSearchInput searchInputs;

   public SupplierInvoiceItemSearchService setSearchInputs(SupplierInvoiceItemSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<SupplierInvoiceItemSearchResult> createTask()
   {
      return new Task<SupplierInvoiceItemSearchResult>()
      {
         @Override
         protected SupplierInvoiceItemSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
