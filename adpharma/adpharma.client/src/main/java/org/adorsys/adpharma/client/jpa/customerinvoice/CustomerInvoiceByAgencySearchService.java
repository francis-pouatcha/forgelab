package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerInvoiceByAgencySearchService extends Service<CustomerInvoiceSearchResult>
{

	@Inject
	private CustomerInvoiceService remoteService;

	private InvoiceByAgencyPrintInput searchInputs;

	private Boolean perday = false ;

	public CustomerInvoiceByAgencySearchService setSearchInputs(InvoiceByAgencyPrintInput searchInputs)
	{
		this.searchInputs = searchInputs;
		return this;
	}

	public CustomerInvoiceByAgencySearchService setPerDay(Boolean perDay)
	{
		this.perday = perDay;
		return this;
	}

	@Override
	protected Task<CustomerInvoiceSearchResult> createTask()
	{
		return new Task<CustomerInvoiceSearchResult>()
				{
			@Override
			protected CustomerInvoiceSearchResult call() throws Exception
			{
				if (searchInputs == null)
					return null;
				if(perday){
					perday = Boolean.FALSE ;
					return remoteService.customerInvicePerDayAndPerAgency(searchInputs);
				}
				return remoteService.findByAgencyAndDateBetween(searchInputs);
			}
				};
	}

}
