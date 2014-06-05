package org.adorsys.adpharma.client.jpa.articleequivalence;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.articleequivalence.ArticleEquivalence;

public class ArticleEquivalenceEditService extends Service<ArticleEquivalence>
{

   @Inject
   private ArticleEquivalenceService remoteService;

   private ArticleEquivalence entity;

   public ArticleEquivalenceEditService setArticleEquivalence(ArticleEquivalence entity)
   {
      this.entity = entity;
      return this;
   }

   @Override
   protected Task<ArticleEquivalence> createTask()
   {
      return new Task<ArticleEquivalence>()
      {
         @Override
         protected ArticleEquivalence call() throws Exception
         {
            if (entity == null)
               return null;
            return remoteService.update(entity);
         }
      };
   }
}
