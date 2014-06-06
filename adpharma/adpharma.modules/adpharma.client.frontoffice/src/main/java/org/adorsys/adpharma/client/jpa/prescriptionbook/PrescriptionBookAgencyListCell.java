package org.adorsys.adpharma.client.jpa.prescriptionbook;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class PrescriptionBookAgencyListCell extends AbstractToStringListCell<PrescriptionBookAgency>
{

   @Override
   protected String getToString(PrescriptionBookAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
