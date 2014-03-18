package org.adorsys.adpharma.client.jpa.productdetailconfig;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class ProductDetailConfigIntialScreenController extends InitialScreenController<ProductDetailConfig>
{
   @Override
   public ProductDetailConfig newEntity()
   {
      return new ProductDetailConfig();
   }
}
