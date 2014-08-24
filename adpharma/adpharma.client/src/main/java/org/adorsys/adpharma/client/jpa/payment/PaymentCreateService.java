package org.adorsys.adpharma.client.jpa.payment;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PaymentCreateService extends Service<Payment>
{

	private Payment model;

	private Boolean isForDebtstatement = false ;

	@Inject
	private PaymentService remoteService;

	public PaymentCreateService setModel(Payment model)
	{
		this.model = model;
		return this;
	}

	public PaymentCreateService setIsForDebtstatement(Boolean isForDebtstatement)
	{
		this.isForDebtstatement = isForDebtstatement;
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
				if (model == null)
					return null;
				if(isForDebtstatement)
					remoteService.createPaymentForDebtstatement(model);
				return remoteService.create(model);
			}
				};
	}
}
