package org.adorsys.adpharma.client.jpa.insurrance;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class InsurranceInsurerListCell extends AbstractToStringListCell<InsurranceInsurer>
{

   @Override
   protected String getToString(InsurranceInsurer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "fullName");
   }

}
