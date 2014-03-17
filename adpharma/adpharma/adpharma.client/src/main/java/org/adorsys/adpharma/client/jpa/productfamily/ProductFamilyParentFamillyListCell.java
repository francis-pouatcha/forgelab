package org.adorsys.adpharma.client.jpa.productfamily;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

public class ProductFamilyParentFamillyListCell extends AbstractToStringListCell<ProductFamily>
{

   @Override
   protected String getToString(ProductFamily item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name", "name");
   }

}
