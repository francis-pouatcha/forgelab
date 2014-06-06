package org.adorsys.adpharma.client.jpa.vat;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class VATEditService extends Service<VAT>
{

   @Inject
   private VATService remoteService;

   private VAT entity;

   public VATEditService setVAT(VAT entity)
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
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
