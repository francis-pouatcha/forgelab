package org.adorsys.adpharma.client.jpa.articleequivalence;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticleEquivalenceLoadService extends Service<ArticleEquivalence>
{

   @Inject
   private ArticleEquivalenceService remoteService;

   private Long id;

   public ArticleEquivalenceLoadService setId(Long id)
   {
      this.id = id;
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
            if (id == null)
               return null;
            return remoteService.findById(id);
         }
      };
   }
}
