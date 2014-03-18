package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

public class ArticleLotRemoveService extends Service<ArticleLot>
{

   @Inject
   private ArticleLotService remoteService;

   private ArticleLot entity;

   public ArticleLotRemoveService setEntity(ArticleLot entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<ArticleLot> createTask()
   {
      return new Task<ArticleLot>()
      {
         @Override
         protected ArticleLot call() throws Exception
         {
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
