package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.Collections;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticleLotSearchService extends Service<ArticleLotSearchResult>
{

   @Inject
   private ArticleLotService remoteService;

   private ArticleLotSearchInput searchInputs;

   public ArticleLotSearchService setSearchInputs(ArticleLotSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<ArticleLotSearchResult> createTask()
   {
      return new Task<ArticleLotSearchResult>()
      {
         @Override
         protected ArticleLotSearchResult call() throws Exception
         {
            if (searchInputs == null)
               return null;
            return remoteService.findByLike(searchInputs);
         }
      };
   }
}
