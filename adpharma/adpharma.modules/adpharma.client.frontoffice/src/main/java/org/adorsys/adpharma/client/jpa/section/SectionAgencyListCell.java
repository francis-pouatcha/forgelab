package org.adorsys.adpharma.client.jpa.section;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class SectionAgencyListCell extends AbstractToStringListCell<SectionAgency>
{

   @Override
   protected String getToString(SectionAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
