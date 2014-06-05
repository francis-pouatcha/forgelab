package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;

public class CustomerInvoiceItemArticleSelectionEventData extends
      AssocSelectionEventData<Article>
{

   public CustomerInvoiceItemArticleSelectionEventData(Object id, Object sourceEntity,
         Article targetEntity)
   {
      super(id, sourceEntity, targetEntity);
   }

}
