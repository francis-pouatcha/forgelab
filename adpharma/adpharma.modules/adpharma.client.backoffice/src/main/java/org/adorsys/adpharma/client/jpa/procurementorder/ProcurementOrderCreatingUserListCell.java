package org.adorsys.adpharma.client.jpa.procurementorder;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class ProcurementOrderCreatingUserListCell extends AbstractToStringListCell<ProcurementOrderCreatingUser>
{

   @Override
   protected String getToString(ProcurementOrderCreatingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
