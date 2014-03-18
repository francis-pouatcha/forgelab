package org.adorsys.adpharma.client.jpa.customer;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerSearchService extends Service<CustomerSearchResult>
{

   @Inject
   private CustomerService remoteService;

   private CustomerSearchInput searchInputs;

   public CustomerSearchService setSearchInputs(CustomerSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<CustomerSearchResult> createTask()
   {
      return new Task<CustomerSearchResult>()
      {
         @Override
         protected CustomerSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
