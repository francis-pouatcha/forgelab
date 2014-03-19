package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DeliveryListSearchService extends Service<List<Delivery>> {

	private DeliveryListSearchInput model;

	@Inject
	private DeliveryService remoteService;

	public DeliveryListSearchService setModel(DeliveryListSearchInput model)
	{
		this.model = model;
		return this;
	}

	@Override
	protected Task<List<Delivery>> createTask() {
		return new Task<List<Delivery>>()
				{
			@Override
			protected List<Delivery> call() throws Exception
			{
				if (model == null)
					return null;
				return remoteService.findByDeliveryDateBetween(model);
			}
				};
	}
}

