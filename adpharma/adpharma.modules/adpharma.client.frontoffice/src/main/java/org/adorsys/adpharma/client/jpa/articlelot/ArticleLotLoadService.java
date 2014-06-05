package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticleLotLoadService extends Service<ArticleLot>
{

   @Inject
   private ArticleLotService remoteService;

   private Long id;

   public ArticleLotLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
