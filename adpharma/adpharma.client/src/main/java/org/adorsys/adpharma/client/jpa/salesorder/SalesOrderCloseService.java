package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.jboss.weld.exceptions.IllegalStateException;

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
