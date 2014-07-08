package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemService;
import org.adorsys.adpharma.client.jpa.salesorder.SalesStatisticsDataSearchResult;
import org.adorsys.adpharma.client.jpa.salesorder.SalesStattisticsDataSearchInput;

@Singleton
public class CustomerInvoiceChartDataService extends Service<SalesStatisticsDataSearchResult>
{

   private SalesStattisticsDataSearchInput model;

   @Inject
   private CustomerInvoiceItemService remoteService;

   public CustomerInvoiceChartDataService setModel(SalesStattisticsDataSearchInput model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<SalesStatisticsDataSearchResult> createTask()
   {
      return new Task<SalesStatisticsDataSearchResult>()
      {
         @Override
         protected SalesStatisticsDataSearchResult call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.findSalesStatistics(model);
         }
      };
   }
}
