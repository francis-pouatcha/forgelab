package org.adorsys.adpharma.client.jpa.agency;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.company.Company;

public class AgencyCompanyListCell extends AbstractToStringListCell<AgencyCompany>
{

   @Override
   protected String getToString(AgencyCompany item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "displayName");
   }

}
