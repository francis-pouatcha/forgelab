package org.adorsys.adpharma.client.jpa.article;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticleLoadService extends Service<Article>
{

   @Inject
   private ArticleService remoteService;

   private Long id;

   public ArticleLoadService setId(Long id)
   {
      this.id = id;
      return this;
   }

   @Override
   protected Task<Article> createTask()
   {
      return new Task<Article>()
      {
         @Override
         protected Article call() throws Exception
         {
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
