package org.adorsys.adpharma.client.jpa.debtstatement;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.customer.Customer;

public class DebtStatementInsurranceListCell extends AbstractToStringListCell<Customer>
{

   @Override
   protected String getToString(Customer item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "fullName");
   }

}
