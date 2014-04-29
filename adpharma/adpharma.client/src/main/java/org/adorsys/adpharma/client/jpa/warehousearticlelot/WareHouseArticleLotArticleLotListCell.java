package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class WareHouseArticleLotArticleLotListCell extends AbstractToStringListCell<WareHouseArticleLotArticleLot>
{

   @Override
   protected String getToString(WareHouseArticleLotArticleLot item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "articleName");
   }

}
