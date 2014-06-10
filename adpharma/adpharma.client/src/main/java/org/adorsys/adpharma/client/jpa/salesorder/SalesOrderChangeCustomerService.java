package org.adorsys.adpharma.client.jpa.salesorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customer.Customer;

public class SalesOrderChangeCustomerService extends Service<SalesOrder> {
	@Inject
	private SalesOrderService remoteService;

	private Customer customer;
	private Long salesId;

	public SalesOrderChangeCustomerService setCustomer(Customer customer)
	{
		this.customer = customer;
		return this;
	}
	
	public SalesOrderChangeCustomerService setSalesId(Long salesId)
	{
		this.salesId = salesId;
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
				if (customer == null || salesId == null )
					return null;
				return remoteService.changeCustomer(salesId, customer);
			}
				};
	}
}
