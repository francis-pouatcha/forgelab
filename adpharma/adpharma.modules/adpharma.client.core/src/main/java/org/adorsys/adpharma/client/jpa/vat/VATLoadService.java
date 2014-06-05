package org.adorsys.adpharma.client.jpa.vat;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class VATLoadService extends Service<VAT>
{

   @Inject
   private VATService remoteService;

   private Long id;

   public VATLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
