package org.adorsys.adpharma.client.jpa.supplier;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SupplierEditService extends Service<Supplier>
{

   @Inject
   private SupplierService remoteService;

   private Supplier entity;

   public SupplierEditService setSupplier(Supplier entity)
   {
      this.entity = entity;
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
