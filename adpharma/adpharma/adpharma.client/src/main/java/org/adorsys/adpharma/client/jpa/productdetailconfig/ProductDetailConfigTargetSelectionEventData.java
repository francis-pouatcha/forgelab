package org.adorsys.adpharma.client.jpa.productdetailconfig;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.article.Article;

public class ProductDetailConfigTargetSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public ProductDetailConfigTargetSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
