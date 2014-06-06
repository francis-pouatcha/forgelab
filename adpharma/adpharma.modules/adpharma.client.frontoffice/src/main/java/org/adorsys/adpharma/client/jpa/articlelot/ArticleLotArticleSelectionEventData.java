package org.adorsys.adpharma.client.jpa.articlelot;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.article.Article;

public class ArticleLotArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public ArticleLotArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
