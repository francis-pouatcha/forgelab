package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProductDetailConfigSearchService extends Service<ProductDetailConfigSearchResult>
{

   @Inject
   private ProductDetailConfigService remoteService;

   private ProductDetailConfigSearchInput searchInputs;

   public ProductDetailConfigSearchService setSearchInputs(ProductDetailConfigSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<ProductDetailConfigSearchResult> createTask()
   {
      return new Task<ProductDetailConfigSearchResult>()
      {
         @Override
         protected ProductDetailConfigSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
