package org.adorsys.adpharma.client.jpa.inventoryitem;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.article.Article;

public class InventoryItemArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public InventoryItemArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
