package org.adorsys.adpharma.client.jpa.customer;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CustomerIntialScreenController extends InitialScreenController<Customer>
{
   @Override
   public Customer newEntity()
   {
      return new Customer();
   }
}
