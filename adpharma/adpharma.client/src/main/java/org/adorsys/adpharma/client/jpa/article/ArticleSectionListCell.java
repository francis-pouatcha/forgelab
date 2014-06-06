package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.section.Section;

public class ArticleSectionListCell extends AbstractToStringListCell<ArticleSection>
{

   @Override
   protected String getToString(ArticleSection item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
