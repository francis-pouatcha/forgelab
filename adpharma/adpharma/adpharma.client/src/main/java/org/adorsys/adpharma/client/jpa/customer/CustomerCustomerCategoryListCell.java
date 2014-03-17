package org.adorsys.adpharma.client.jpa.customer;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;

public class CustomerCustomerCategoryListCell extends AbstractToStringListCell<CustomerCategory>
{

   @Override
   protected String getToString(CustomerCategory item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
