package org.adorsys.adpharma.client.jpa.payment;

import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public class PaymentCustomerService extends Service<Payment> {

	@Inject
	   private PaymentService remoteService;

	   private Long id;
	   
	   private List<CustomerInvoice> customerInvoices;

	   public PaymentCustomerService setInvoices(List<CustomerInvoice> customerInvoices)
	   {
	      this.customerInvoices = customerInvoices;
	      return this;
	   }
	   
	   public PaymentCustomerService setEntityId(Long id)
	   {
	      this.id = id;
	      return this;
	   }

	   @Override
	   protected Task<Payment> createTask()
	   {
	      return new Task<Payment>()
	      {
	         @Override
	         protected Payment call() throws Exception
	         {
//	            return remoteService.customerPayment(id,customerInvoices);
	        	 return null;
	         }
	      };
	   }
}
