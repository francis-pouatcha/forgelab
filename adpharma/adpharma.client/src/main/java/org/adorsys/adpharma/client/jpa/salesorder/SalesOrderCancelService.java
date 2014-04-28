package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SalesOrderCancelService extends Service<SalesOrder> {
	@Inject
	private SalesOrderService remoteService;

	private SalesOrder entity;

	public SalesOrderCancelService setSalesOrder(SalesOrder entity)
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
				return remoteService.cancel(entity);
			}
				};
	}

}
