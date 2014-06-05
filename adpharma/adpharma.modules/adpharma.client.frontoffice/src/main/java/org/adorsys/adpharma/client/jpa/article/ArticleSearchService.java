package org.adorsys.adpharma.client.jpa.article;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticleSearchService extends Service<ArticleSearchResult>
{

   @Inject
   private ArticleService remoteService;

   private ArticleSearchInput searchInputs;

   public ArticleSearchService setSearchInputs(ArticleSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<ArticleSearchResult> createTask()
   {
      return new Task<ArticleSearchResult>()
      {
         @Override
         protected ArticleSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
