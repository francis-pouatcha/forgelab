package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CustomerInvoiceByAgencySearchService extends Service<CustomerInvoiceSearchResult>
{

	@Inject
	private CustomerInvoiceService remoteService;

	private InvoiceByAgencyPrintInput searchInputs;

	public CustomerInvoiceByAgencySearchService setSearchInputs(InvoiceByAgencyPrintInput searchInputs)
	{
		this.searchInputs = searchInputs;
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
				return remoteService.findByAgencyAndDateBetween(searchInputs);
			}
				};
	}

}
