package org.adorsys.adpharma.client.jpa.agency;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.company.Company;

public class AgencyCompanyListCell extends AbstractToStringListCell<Company>
{

   @Override
   protected String getToString(Company item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "displayName");
   }

}
