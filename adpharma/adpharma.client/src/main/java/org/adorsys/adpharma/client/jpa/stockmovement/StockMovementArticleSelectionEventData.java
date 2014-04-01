package org.adorsys.adpharma.client.jpa.stockmovement;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.article.Article;

public class StockMovementArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public StockMovementArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
