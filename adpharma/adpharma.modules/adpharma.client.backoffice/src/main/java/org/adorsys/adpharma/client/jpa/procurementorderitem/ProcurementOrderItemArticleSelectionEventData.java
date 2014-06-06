package org.adorsys.adpharma.client.jpa.procurementorderitem;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class ProcurementOrderItemArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public ProcurementOrderItemArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
