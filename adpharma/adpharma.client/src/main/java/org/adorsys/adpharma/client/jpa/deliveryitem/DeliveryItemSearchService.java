package org.adorsys.adpharma.client.jpa.deliveryitem;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.delivery.PeriodicalDeliveryDataSearchInput;

public class DeliveryItemSearchService extends Service<DeliveryItemSearchResult>
{

	@Inject
	private DeliveryItemService remoteService;

	private DeliveryItemSearchInput searchInputs;

	private PeriodicalDeliveryDataSearchInput data ;

	public DeliveryItemSearchService setSearchInputs(DeliveryItemSearchInput searchInputs)
	{
		this.searchInputs = searchInputs;
		return this;
	}

	public DeliveryItemSearchService setData(PeriodicalDeliveryDataSearchInput data)
	{
		this.data = data;
		return this;
	}

	@Override
	protected Task<DeliveryItemSearchResult> createTask()
	{
		return new Task<DeliveryItemSearchResult>()
				{
			@Override
			protected DeliveryItemSearchResult call() throws Exception
			{
				if (searchInputs != null)
					return remoteService.findByLike(searchInputs);
				if (data != null)
					return remoteService.periodicalDeliveryRepport(data);
				return null;
			}
				};
	}
}
