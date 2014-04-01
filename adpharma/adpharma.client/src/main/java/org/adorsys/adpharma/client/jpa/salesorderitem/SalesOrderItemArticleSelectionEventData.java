package org.adorsys.adpharma.client.jpa.salesorderitem;

import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

import org.adorsys.adpharma.client.jpa.article.Article;

public class SalesOrderItemArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public SalesOrderItemArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
