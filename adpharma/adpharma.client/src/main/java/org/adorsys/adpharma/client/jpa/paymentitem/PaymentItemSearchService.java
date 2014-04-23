package org.adorsys.adpharma.client.jpa.paymentitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PaymentItemSearchService extends Service<PaymentItemSearchResult>
{

   @Inject
   private PaymentItemService remoteService;

   private PaymentItemSearchInput searchInputs;

   public PaymentItemSearchService setSearchInputs(PaymentItemSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<PaymentItemSearchResult> createTask()
   {
      return new Task<PaymentItemSearchResult>()
      {
         @Override
         protected PaymentItemSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
