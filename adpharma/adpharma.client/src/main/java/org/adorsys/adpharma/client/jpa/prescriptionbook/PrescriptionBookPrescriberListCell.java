package org.adorsys.adpharma.client.jpa.prescriptionbook;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;

public class PrescriptionBookPrescriberListCell extends AbstractToStringListCell<PrescriptionBookPrescriber>
{

   @Override
   protected String getToString(PrescriptionBookPrescriber item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
