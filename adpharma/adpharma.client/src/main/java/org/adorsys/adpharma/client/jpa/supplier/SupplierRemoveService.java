package org.adorsys.adpharma.client.jpa.supplier;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public class SupplierRemoveService extends Service<Supplier>
{

   @Inject
   private SupplierService remoteService;

   private Supplier entity;

   public SupplierRemoveService setEntity(Supplier entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
