package org.adorsys.adpharma.client.jpa.inventoryitem;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class InventoryItemArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public InventoryItemArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
