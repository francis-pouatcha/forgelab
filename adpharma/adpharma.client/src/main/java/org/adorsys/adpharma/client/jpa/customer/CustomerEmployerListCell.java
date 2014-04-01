package org.adorsys.adpharma.client.jpa.customer;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.employer.Employer;

public class CustomerEmployerListCell extends AbstractToStringListCell<CustomerEmployer>
{

   @Override
   protected String getToString(CustomerEmployer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
