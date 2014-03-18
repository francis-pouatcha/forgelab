package org.adorsys.adpharma.client.jpa.customercategory;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerCategorySearchService extends Service<CustomerCategorySearchResult>
{

   @Inject
   private CustomerCategoryService remoteService;

   private CustomerCategorySearchInput searchInputs;

   public CustomerCategorySearchService setSearchInputs(CustomerCategorySearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<CustomerCategorySearchResult> createTask()
   {
      return new Task<CustomerCategorySearchResult>()
      {
         @Override
         protected CustomerCategorySearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
