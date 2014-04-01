package org.adorsys.adpharma.client.jpa.prescriptionbook;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.hospital.Hospital;

public class PrescriptionBookHospitalListCell extends AbstractToStringListCell<PrescriptionBookHospital>
{

   @Override
   protected String getToString(PrescriptionBookHospital item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
