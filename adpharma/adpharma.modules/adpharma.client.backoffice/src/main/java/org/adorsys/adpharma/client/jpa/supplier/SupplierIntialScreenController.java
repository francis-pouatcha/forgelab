package org.adorsys.adpharma.client.jpa.supplier;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class SupplierIntialScreenController extends InitialScreenController<Supplier>
{
   @Override
   public Supplier newEntity()
   {
      return new Supplier();
   }
}
