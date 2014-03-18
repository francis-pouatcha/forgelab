package org.adorsys.adpharma.client.jpa.articleequivalence;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.adorsys.adpharma.client.jpa.articleequivalence.ArticleEquivalence;

@Singleton
public class ArticleEquivalenceCreateService extends Service<ArticleEquivalence>
{

   private ArticleEquivalence model;

   @Inject
   private ArticleEquivalenceService remoteService;

   public ArticleEquivalenceCreateService setModel(ArticleEquivalence model)
   {
      this.model = model;
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
            if (model == null)
               return null;
            return remoteService.create(model);
         }
      };
   }
}
