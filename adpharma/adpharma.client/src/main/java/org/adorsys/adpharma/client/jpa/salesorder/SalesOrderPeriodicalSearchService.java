package org.adorsys.adpharma.client.jpa.salesorder;

import javax.inject.Inject;


import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class SalesOrderPeriodicalSearchService extends Service<SalesOrderDiscountSearchResult> {
	
	
	@Inject
	private SalesOrderService remoteService;
	
	private PeriodicalDataSearchInput searchInputs;
	

	public SalesOrderPeriodicalSearchService setSearchInputs(PeriodicalDataSearchInput searchInputs) {
		this.searchInputs=searchInputs;
		
		return this;
	}

	@Override
	protected Task<SalesOrderDiscountSearchResult> createTask() {
		return new Task<SalesOrderDiscountSearchResult>() {

			@Override
			protected SalesOrderDiscountSearchResult call() throws Exception {
				if (searchInputs == null)
		               return null;
				return remoteService.periodicalSalesPerVendor(searchInputs);
			}
		};
	}
	
	

}
