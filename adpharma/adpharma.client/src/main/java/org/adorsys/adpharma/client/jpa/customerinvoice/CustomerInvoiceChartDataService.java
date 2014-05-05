package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.utils.ChartData;
import org.adorsys.adpharma.client.utils.ChartDataSearchInput;

@Singleton
public class CustomerInvoiceChartDataService extends Service<List<ChartData>>
{

   private ChartDataSearchInput model;

   @Inject
   private CustomerInvoiceService remoteService;

   public CustomerInvoiceChartDataService setModel(ChartDataSearchInput model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<List<ChartData>> createTask()
   {
      return new Task<List<ChartData>>()
      {
         @Override
         protected List<ChartData> call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.findSalesStatistics (model);
         }
      };
   }
}
