package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javax.inject.Inject;


import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PrescriptionBookPeriodicalSearchService extends Service<PrescriptionBookSearchResult> {
	
	
	@Inject
	PrescriptionBookService remoteService;
	
	private PeriodicalPrescriptionBookDataSearchInput searchInputs;
	
	public PrescriptionBookPeriodicalSearchService setSearchInputs(PeriodicalPrescriptionBookDataSearchInput searchInputs)
	   {
	      this.searchInputs = searchInputs;
	      return this;
	   }
	
	

	@Override
	protected Task<PrescriptionBookSearchResult> createTask() {
		
		return new Task<PrescriptionBookSearchResult>() {

			@Override
			protected PrescriptionBookSearchResult call() throws Exception {
				if(searchInputs==null) return null;
				return	remoteService.periodicalPrescriptionBook(searchInputs);
			}
		};
		
	}

}
