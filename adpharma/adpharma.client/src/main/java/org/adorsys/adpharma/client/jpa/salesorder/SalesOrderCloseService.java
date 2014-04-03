package org.adorsys.adpharma.client.jpa.salesorder;

import javax.inject.Inject;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryCloseService;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryService;

public class SalesOrderCloseService extends Service<SalesOrder> {
	@Inject
	private SalesOrderService remoteService;

	private SalesOrder entity;

	public SalesOrderCloseService setSalesOrder(SalesOrder entity)
	{
		this.entity = entity;
		return this;
	}

	@Override
	protected Task<SalesOrder> createTask()
	{
		return new Task<SalesOrder>()
				{
			@Override
			protected SalesOrder call() throws Exception
			{
				if (entity == null)
					return null;
				return remoteService.saveAndClose(entity);
			}
				};
	}
}
