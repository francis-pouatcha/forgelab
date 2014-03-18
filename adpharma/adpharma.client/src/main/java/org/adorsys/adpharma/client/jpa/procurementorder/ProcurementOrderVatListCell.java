package org.adorsys.adpharma.client.jpa.procurementorder;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.vat.VAT;

public class ProcurementOrderVatListCell extends AbstractToStringListCell<VAT>
{

   @Override
   protected String getToString(VAT item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
