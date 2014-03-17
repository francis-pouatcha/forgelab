package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.article.Article;

public class SupplierInvoiceItemArticleListCell extends AbstractToStringListCell<Article>
{

   @Override
   protected String getToString(Article item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "articleName", "pic");
   }

}
