package org.adorsys.adpharma.client.jpa.procurementorder;


import javax.inject.Inject;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ProcurementOrderRetrievedService extends Service<ProcurementOrder> {

	private ProcurementOrder model;
	
	@Inject
	private ProcurementOrderService remoteService;
	
	public ProcurementOrderRetrievedService setModel(ProcurementOrder model)
	{
		this.model = model;
		return this;
	}
	
	
	@Override
	protected Task<ProcurementOrder> createTask() {
		return new Task<ProcurementOrder>() {

			@Override
			protected ProcurementOrder call() throws Exception {
				if(model==null) return null;
				ProcurementOrder procurementOrder = remoteService.retrievedPreparationOrder(model); 
				return procurementOrder;
			}
		};
	}
	

}
