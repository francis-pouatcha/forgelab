package org.adorsys.adpharma.client.jpa.deliveryitem;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.article.Article;

public class DeliveryItemArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public DeliveryItemArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
