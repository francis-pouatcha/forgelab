package org.adorsys.adpharma.client.jpa.procurementorder;

import javax.inject.Inject;


import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class ProcurementAdvancedSearchService extends Service<ProcurementOrderSearchResult>{
	
	@Inject
	private ProcurementOrderService remoteService;
	
	private ProcurementOrderAdvancedSearchData searchInputs;
	
	public ProcurementAdvancedSearchService setSearchInputs(ProcurementOrderAdvancedSearchData searchInputs) {
		this.searchInputs = searchInputs;
		return this;
	}
	

	@Override
	protected Task<ProcurementOrderSearchResult> createTask() {
		return new Task<ProcurementOrderSearchResult>() {
			@Override
			protected ProcurementOrderSearchResult call() throws Exception {
				if(searchInputs==null) return null;
				return remoteService.advenceSearch(searchInputs);
			}
		};
	}
	

}
