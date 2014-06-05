package org.adorsys.adpharma.client.jpa.stockmovement;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class StockMovementArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public StockMovementArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
