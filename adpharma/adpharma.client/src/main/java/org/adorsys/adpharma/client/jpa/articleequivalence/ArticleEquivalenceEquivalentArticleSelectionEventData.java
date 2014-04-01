package org.adorsys.adpharma.client.jpa.articleequivalence;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleEquivalenceEquivalentArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public ArticleEquivalenceEquivalentArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
