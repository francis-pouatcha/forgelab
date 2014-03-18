package org.adorsys.adpharma.client.jpa.currency;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.currency.Currency;

public class CurrencyEditService extends Service<Currency>
{

   @Inject
   private CurrencyService remoteService;

   private Currency entity;

   public CurrencyEditService setCurrency(Currency entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
