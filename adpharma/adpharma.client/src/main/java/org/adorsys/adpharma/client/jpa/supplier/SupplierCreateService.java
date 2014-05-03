package org.adorsys.adpharma.client.jpa.supplier;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SupplierCreateService extends Service<Supplier>
{

   private Supplier model;

   @Inject
   private SupplierService remoteService;

   public SupplierCreateService setModel(Supplier model)
   {
      this.model = model;
      return this;
   }

   @Override
   protected Task<Supplier> createTask()
   {
      return new Task<Supplier>()
      {
         @Override
         protected Supplier call() throws Exception
         {
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
