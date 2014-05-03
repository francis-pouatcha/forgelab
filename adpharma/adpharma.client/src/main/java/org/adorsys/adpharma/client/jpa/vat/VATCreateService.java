package org.adorsys.adpharma.client.jpa.vat;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VATCreateService extends Service<VAT>
{

   private VAT model;

   @Inject
   private VATService remoteService;

   public VATCreateService setModel(VAT model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<VAT> createTask()
   {
      return new Task<VAT>()
      {
         @Override
         protected VAT call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
