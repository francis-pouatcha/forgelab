package org.adorsys.adpharma.client.jpa.payment;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class PaymentSearchService extends Service<PaymentSearchResult>
{

   @Inject
   private PaymentService remoteService;

   private PaymentSearchInput searchInputs;

   public PaymentSearchService setSearchInputs(PaymentSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<PaymentSearchResult> createTask()
   {
      return new Task<PaymentSearchResult>()
      {
         @Override
         protected PaymentSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
