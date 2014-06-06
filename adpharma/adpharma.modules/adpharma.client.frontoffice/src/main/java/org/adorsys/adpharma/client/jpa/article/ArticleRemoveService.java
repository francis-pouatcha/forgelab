package org.adorsys.adpharma.client.jpa.article;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleRemoveService extends Service<Article>
{

   @Inject
   private ArticleService remoteService;

   private Article entity;

   public ArticleRemoveService setEntity(Article entity)
   {
      this.entity = entity;
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
