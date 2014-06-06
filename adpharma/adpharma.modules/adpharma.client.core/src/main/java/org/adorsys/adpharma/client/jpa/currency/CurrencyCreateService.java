package org.adorsys.adpharma.client.jpa.currency;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.currency.Currency;

@Singleton
public class CurrencyCreateService extends Service<Currency>
{

   private Currency model;

   @Inject
   private CurrencyService remoteService;

   public CurrencyCreateService setModel(Currency model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<Currency> createTask()
   {
      return new Task<Currency>()
      {
         @Override
         protected Currency call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
