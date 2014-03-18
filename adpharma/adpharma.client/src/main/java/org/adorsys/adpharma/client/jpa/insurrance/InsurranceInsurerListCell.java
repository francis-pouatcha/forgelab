package org.adorsys.adpharma.client.jpa.insurrance;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.customer.Customer;

public class InsurranceInsurerListCell extends AbstractToStringListCell<Customer>
{

   @Override
   protected String getToString(Customer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "fullName");
   }

}
