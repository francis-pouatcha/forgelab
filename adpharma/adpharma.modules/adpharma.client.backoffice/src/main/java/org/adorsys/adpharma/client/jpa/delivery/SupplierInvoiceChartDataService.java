package org.adorsys.adpharma.client.jpa.delivery;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoiceService;

@Singleton
public class SupplierInvoiceChartDataService  extends Service<DeliveryStatisticsDataSearchResult>
{

	   private DeliveryStattisticsDataSearchInput model;

	   @Inject
	   private SupplierInvoiceService remoteService;

	   public SupplierInvoiceChartDataService setModel(DeliveryStattisticsDataSearchInput model)
	   {
	      this.model = model;
	      return this;
	   }

	   @Override
	   protected Task<DeliveryStatisticsDataSearchResult> createTask()
	   {
	      return new Task<DeliveryStatisticsDataSearchResult>()
	      {
	         @Override
	         protected DeliveryStatisticsDataSearchResult call() throws Exception
	         {
	            if (model == null)
	               return null;
	            return remoteService.findSalesStatistics(model);
	         }
	      };
	   }

}
