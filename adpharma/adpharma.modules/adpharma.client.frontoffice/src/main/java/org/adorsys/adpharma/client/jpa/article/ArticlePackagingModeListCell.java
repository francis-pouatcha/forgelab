package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;

public class ArticlePackagingModeListCell extends AbstractToStringListCell<ArticlePackagingMode>
{

   @Override
   protected String getToString(ArticlePackagingMode item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
