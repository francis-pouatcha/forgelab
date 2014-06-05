package org.adorsys.adpharma.client.jpa.articleequivalence;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import org.adorsys.adpharma.client.jpa.articleequivalence.ArticleEquivalence;

public class ArticleEquivalenceRemoveService extends Service<ArticleEquivalence>
{

   @Inject
   private ArticleEquivalenceService remoteService;

   private ArticleEquivalence entity;

   public ArticleEquivalenceRemoveService setEntity(ArticleEquivalence entity)
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
            return remoteService.deleteById(entity.getId());
         }
      };
   }
}
