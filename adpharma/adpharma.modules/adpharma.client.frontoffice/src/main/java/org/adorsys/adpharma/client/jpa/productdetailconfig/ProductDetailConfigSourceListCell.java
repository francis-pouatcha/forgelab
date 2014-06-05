package org.adorsys.adpharma.client.jpa.productdetailconfig;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class ProductDetailConfigSourceListCell extends AbstractToStringListCell<ProductDetailConfigSource>
{

	   @Override
	   protected String getToString(ProductDetailConfigSource item)
	   {
	      if (item == null)
	      {
	         return "";
	      }
	      return PropertyReader.buildToString(item, "articleName");
	   }


}
