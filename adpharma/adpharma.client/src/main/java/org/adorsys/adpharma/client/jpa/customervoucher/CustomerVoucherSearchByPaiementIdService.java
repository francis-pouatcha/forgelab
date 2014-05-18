package org.adorsys.adpharma.client.jpa.customervoucher;

import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerVoucherSearchByPaiementIdService extends Service<List<CustomerVoucher>>
{

	   @Inject
	   private CustomerVoucherService remoteService;

	   private Long paiementId;

	   public CustomerVoucherSearchByPaiementIdService setPaiementId(Long paiementId)
	   {
	      this.paiementId = paiementId;
	      return this;
	   }

	   @Override
	   protected Task<List<CustomerVoucher>> createTask()
	   {
	      return new Task<List<CustomerVoucher>>()
	      {
	         @Override
	         protected List<CustomerVoucher> call() throws Exception
	         {
	            if (paiementId == null)
	               return null;
	            return remoteService.findByPaiementId(paiementId);
	         }
	      };
	   }

}
