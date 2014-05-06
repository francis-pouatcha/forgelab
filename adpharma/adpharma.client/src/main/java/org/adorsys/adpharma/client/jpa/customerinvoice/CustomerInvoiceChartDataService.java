package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.salesorder.SalesStatisticsDataSearchResult;
import org.adorsys.adpharma.client.jpa.salesorder.SalesStattisticsDataSearchInput;
import org.adorsys.adpharma.client.utils.ChartData;

@Singleton
public class CustomerInvoiceChartDataService extends Service<SalesStatisticsDataSearchResult>
{

   private SalesStattisticsDataSearchInput model;

   @Inject
   private CustomerInvoiceService remoteService;

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
