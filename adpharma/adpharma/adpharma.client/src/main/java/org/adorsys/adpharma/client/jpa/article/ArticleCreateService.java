package org.adorsys.adpharma.client.jpa.article;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.article.Article;

@Singleton
public class ArticleCreateService extends Service<Article>
{

   private Article model;

   @Inject
   private ArticleService remoteService;

   public ArticleCreateService setModel(Article model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
