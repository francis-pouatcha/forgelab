package org.adorsys.adpharma.client.jpa.productdetailconfig;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class ProductDetailConfigSourceSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public ProductDetailConfigSourceSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
