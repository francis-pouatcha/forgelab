package org.adorsys.adpharma.client.jpa.productfamily;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProductFamilySearchService extends Service<ProductFamilySearchResult>
{

   @Inject
   private ProductFamilyService remoteService;

   private ProductFamilySearchInput searchInputs;

   public ProductFamilySearchService setSearchInputs(ProductFamilySearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<ProductFamilySearchResult> createTask()
   {
      return new Task<ProductFamilySearchResult>()
      {
         @Override
         protected ProductFamilySearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
