package org.adorsys.adpharma.client.jpa.paymentcustomerinvoiceassoc;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchResult;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchService;
import org.adorsys.adpharma.client.jpa.customer.CustomerService;

public class PaymentCustomerInvoiceAssocSearchService extends Service<PaymentCustomerInvoiceAssocSearchResult>
{

	   @Inject
	   private PaymentCustomerInvoiceAssocService remoteService;

	   private PaymentCustomerInvoiceAssocSearchInput searchInputs;

	   public PaymentCustomerInvoiceAssocSearchService setSearchInputs(PaymentCustomerInvoiceAssocSearchInput searchInputs)
	   {
	      this.searchInputs = searchInputs;
	      return this;
	   }

	   @Override
	   protected Task<PaymentCustomerInvoiceAssocSearchResult> createTask()
	   {
	      return new Task<PaymentCustomerInvoiceAssocSearchResult>()
	      {
	         @Override
	         protected PaymentCustomerInvoiceAssocSearchResult call() throws Exception
	         {
	            if (searchInputs == null)
	               return null;
	            return remoteService.findByLike(searchInputs);
	         }
	      };
	   }
	}
