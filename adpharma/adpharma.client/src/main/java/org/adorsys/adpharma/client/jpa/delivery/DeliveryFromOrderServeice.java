package org.adorsys.adpharma.client.jpa.delivery;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

@Singleton
public class DeliveryFromOrderServeice extends Service<Delivery>
{

	private ProcurementOrder model;

	@Inject
	private DeliveryService remoteService;

	public DeliveryFromOrderServeice setModel(ProcurementOrder model)
	{
		this.model = model;
		return this;
	}

	@Override
	protected Task<Delivery> createTask()
	{
		return new Task<Delivery>()
				{
			@Override
			protected Delivery call() throws Exception
			{
				if (model == null)
					return null;
				return remoteService.deliveryFromProcurementOrder(model);
			}
				};
	}

}
