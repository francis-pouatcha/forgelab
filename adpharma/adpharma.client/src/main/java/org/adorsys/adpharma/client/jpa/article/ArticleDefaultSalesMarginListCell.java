package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;

public class ArticleDefaultSalesMarginListCell extends AbstractToStringListCell<SalesMargin>
{

   @Override
   protected String getToString(SalesMargin item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
