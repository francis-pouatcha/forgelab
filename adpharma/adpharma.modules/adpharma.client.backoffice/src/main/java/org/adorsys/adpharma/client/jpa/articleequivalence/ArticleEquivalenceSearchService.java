package org.adorsys.adpharma.client.jpa.articleequivalence;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticleEquivalenceSearchService extends Service<ArticleEquivalenceSearchResult>
{

   @Inject
   private ArticleEquivalenceService remoteService;

   private ArticleEquivalenceSearchInput searchInputs;

   public ArticleEquivalenceSearchService setSearchInputs(ArticleEquivalenceSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<ArticleEquivalenceSearchResult> createTask()
   {
      return new Task<ArticleEquivalenceSearchResult>()
      {
         @Override
         protected ArticleEquivalenceSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
