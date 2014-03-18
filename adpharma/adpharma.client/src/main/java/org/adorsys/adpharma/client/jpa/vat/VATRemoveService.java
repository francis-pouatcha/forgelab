package org.adorsys.adpharma.client.jpa.vat;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.vat.VAT;

public class VATRemoveService extends Service<VAT>
{

   @Inject
   private VATService remoteService;

   private VAT entity;

   public VATRemoveService setEntity(VAT entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
