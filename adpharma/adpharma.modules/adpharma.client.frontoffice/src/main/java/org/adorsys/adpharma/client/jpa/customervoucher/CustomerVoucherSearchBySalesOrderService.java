package org.adorsys.adpharma.client.jpa.customervoucher;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public class CustomerVoucherSearchBySalesOrderService extends Service<CustomerVoucher>
{

	@Inject
	private CustomerVoucherService remoteService;

	private SalesOrder salesOrder;

	public CustomerVoucherSearchBySalesOrderService setSalesOrder(SalesOrder salesOrder)
	{
		this.salesOrder = salesOrder;
		return this;
	}

	@Override
	protected Task<CustomerVoucher> createTask()
	{
		return new Task<CustomerVoucher>()
				{
			@Override
			protected CustomerVoucher call() throws Exception
			{
				if (salesOrder == null)
					return null;
				return remoteService.findBySalesOrder(salesOrder);
			}
				};
	}

}
