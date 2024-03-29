package org.adorsys.adpharma.client.jpa.cashdrawer;

import javax.inject.Inject;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CashDrawerCloseService  extends Service<CashDrawer>{
	@Inject
	private CashDrawerService remoteService;

	private CashDrawer entity;

	public CashDrawerCloseService setCashDrawer(CashDrawer entity)
	{
		this.entity = entity;
		return this;
	}

	@Override
	protected Task<CashDrawer> createTask()
	{
		return new Task<CashDrawer>()
				{
			@Override
			protected CashDrawer call() throws Exception
			{
				if (entity == null)
					return null;
				return remoteService.close(entity);
			}
				};
	}
}
