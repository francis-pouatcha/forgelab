package org.adorsys.adpharma.client.jpa.articleequivalence;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleEquivalenceMainArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public ArticleEquivalenceMainArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
