package org.adorsys.adpharma.client.jpa.debtstatement;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DebtStatementCreateService extends Service<DebtStatement>
{

	private DebtStatement model;

	private DebtStatementProcessingData data ;

	@Inject
	private DebtStatementService remoteService;

	public DebtStatementCreateService setModel(DebtStatement model)
	{
		this.model = model;
		return this;
	}

	public DebtStatementCreateService setData(DebtStatementProcessingData data)
	{
		this.data = data;
		return this;
	}

	@Override
	protected Task<DebtStatement> createTask()
	{
		return new Task<DebtStatement>()
				{
			@Override
			protected DebtStatement call() throws Exception
			{
				if (model != null)
					return remoteService.create(model);
				if (data != null)
					return remoteService.createDebtStatement(data);
				return null;
			}
				};
	}
}
