package org.adorsys.adpharma.client.jpa.customer;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.employer.Employer;

public class CustomerEmployerListCell extends AbstractToStringListCell<Employer>
{

   @Override
   protected String getToString(Employer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
