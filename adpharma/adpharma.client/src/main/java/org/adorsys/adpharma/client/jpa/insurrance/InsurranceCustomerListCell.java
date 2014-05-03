package org.adorsys.adpharma.client.jpa.insurrance;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class InsurranceCustomerListCell extends AbstractToStringListCell<InsurranceCustomer>
{

   @Override
   protected String getToString(InsurranceCustomer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "fullName");
   }

}
