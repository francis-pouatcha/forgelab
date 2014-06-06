package org.adorsys.adpharma.client.jpa.productdetailconfig;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class ProductDetailConfigTargetListCell extends AbstractToStringListCell<ProductDetailConfigTarget>
{

	   @Override
	   protected String getToString(ProductDetailConfigTarget item)
	   {
	      if (item == null)
	      {
	         return "";
	      }
	      return PropertyReader.buildToString(item, "articleName");
	   }


}
