package org.adorsys.adpharma.client.jpa.procurementorder;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class ProcurementOrderVatListCell extends AbstractToStringListCell<ProcurementOrderVat>
{

   @Override
   protected String getToString(ProcurementOrderVat item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
