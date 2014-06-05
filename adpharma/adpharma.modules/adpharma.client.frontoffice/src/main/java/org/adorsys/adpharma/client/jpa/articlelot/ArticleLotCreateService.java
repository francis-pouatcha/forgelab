package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

@Singleton
public class ArticleLotCreateService extends Service<ArticleLot>
{

   private ArticleLot model;

   @Inject
   private ArticleLotService remoteService;

   public ArticleLotCreateService setModel(ArticleLot model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
