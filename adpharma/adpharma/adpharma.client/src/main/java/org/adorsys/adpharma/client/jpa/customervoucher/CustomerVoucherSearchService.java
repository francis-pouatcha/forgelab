package org.adorsys.adpharma.client.jpa.customervoucher;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerVoucherSearchService extends Service<CustomerVoucherSearchResult>
{

   @Inject
   private CustomerVoucherService remoteService;

   private CustomerVoucherSearchInput searchInputs;

   public CustomerVoucherSearchService setSearchInputs(CustomerVoucherSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<CustomerVoucherSearchResult> createTask()
   {
      return new Task<CustomerVoucherSearchResult>()
      {
         @Override
         protected CustomerVoucherSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
