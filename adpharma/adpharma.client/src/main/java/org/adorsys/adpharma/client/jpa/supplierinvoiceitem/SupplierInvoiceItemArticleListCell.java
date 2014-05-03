package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class SupplierInvoiceItemArticleListCell extends AbstractToStringListCell<SupplierInvoiceItemArticle>
{

   @Override
   protected String getToString(SupplierInvoiceItemArticle item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "articleName", "pic");
   }

}
