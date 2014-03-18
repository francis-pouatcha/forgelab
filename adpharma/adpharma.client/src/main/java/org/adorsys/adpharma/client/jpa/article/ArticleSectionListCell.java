package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.section.Section;

public class ArticleSectionListCell extends AbstractToStringListCell<Section>
{

   @Override
   protected String getToString(Section item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "sectionCode");
   }

}
