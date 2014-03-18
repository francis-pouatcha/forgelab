package org.adorsys.adpharma.client.jpa.customercategory;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CustomerCategoryIntialScreenController extends InitialScreenController<CustomerCategory>
{
   @Override
   public CustomerCategory newEntity()
   {
      return new CustomerCategory();
   }
}
