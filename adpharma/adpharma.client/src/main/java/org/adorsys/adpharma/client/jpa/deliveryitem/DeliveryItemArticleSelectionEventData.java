package org.adorsys.adpharma.client.jpa.deliveryitem;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class DeliveryItemArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public DeliveryItemArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
