package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;

public class ArticleClearanceConfigListCell extends AbstractToStringListCell<ArticleClearanceConfig>
{

   @Override
   protected String getToString(ArticleClearanceConfig item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item,  "discountRate");
   }

}
