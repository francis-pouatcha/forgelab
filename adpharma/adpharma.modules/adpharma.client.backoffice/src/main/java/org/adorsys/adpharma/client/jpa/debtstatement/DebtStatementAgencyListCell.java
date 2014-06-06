package org.adorsys.adpharma.client.jpa.debtstatement;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class DebtStatementAgencyListCell extends AbstractToStringListCell<DebtStatementAgency>
{

   @Override
   protected String getToString(DebtStatementAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
