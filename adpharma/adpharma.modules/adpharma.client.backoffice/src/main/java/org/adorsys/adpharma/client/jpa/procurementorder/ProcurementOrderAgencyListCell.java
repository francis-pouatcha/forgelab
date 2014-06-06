package org.adorsys.adpharma.client.jpa.procurementorder;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class ProcurementOrderAgencyListCell extends AbstractToStringListCell<ProcurementOrderAgency>
{

   @Override
   protected String getToString(ProcurementOrderAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
