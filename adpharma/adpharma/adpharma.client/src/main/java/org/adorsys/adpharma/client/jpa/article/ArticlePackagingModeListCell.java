package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;

public class ArticlePackagingModeListCell extends AbstractToStringListCell<PackagingMode>
{

   @Override
   protected String getToString(PackagingMode item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
