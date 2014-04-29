package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class WareHouseArticleLotWareHouseListCell extends AbstractToStringListCell<WareHouseArticleLotWareHouse>
{

   @Override
   protected String getToString(WareHouseArticleLotWareHouse item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
