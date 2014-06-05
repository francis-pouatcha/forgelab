package org.adorsys.adpharma.client.jpa.productfamily;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class ProductFamilyIntialScreenController extends InitialScreenController<ProductFamily>
{
   @Override
   public ProductFamily newEntity()
   {
      return new ProductFamily();
   }
}
