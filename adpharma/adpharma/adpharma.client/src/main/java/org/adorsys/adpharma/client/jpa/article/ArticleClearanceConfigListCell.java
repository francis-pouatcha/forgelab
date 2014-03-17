package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;

public class ArticleClearanceConfigListCell extends AbstractToStringListCell<ClearanceConfig>
{

   @Override
   protected String getToString(ClearanceConfig item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "startDate", "endDate", "discountRate", "clearanceState");
   }

}
