package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;

public class ArticleFamilyListCell extends AbstractToStringListCell<ArticleFamily>
{

   @Override
   protected String getToString(ArticleFamily item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
