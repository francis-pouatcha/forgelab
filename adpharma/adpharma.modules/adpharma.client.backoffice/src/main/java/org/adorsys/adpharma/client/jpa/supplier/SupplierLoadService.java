package org.adorsys.adpharma.client.jpa.supplier;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class SupplierLoadService extends Service<Supplier>
{

   @Inject
   private SupplierService remoteService;

   private Long id;

   public SupplierLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
