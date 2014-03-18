package org.adorsys.adpharma.client.jpa.procurementorderitem;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class ProcurementOrderItemCreatingUserListCell extends AbstractToStringListCell<Login>
{

   @Override
   protected String getToString(Login item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}