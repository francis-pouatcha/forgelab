package org.adorsys.adpharma.client.jpa.currency;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class CurrencyLoadService extends Service<Currency>
{

   @Inject
   private CurrencyService remoteService;

   private Long id;

   public CurrencyLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
