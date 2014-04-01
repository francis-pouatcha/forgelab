package org.adorsys.adpharma.client.jpa.prescriptionbook;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class PrescriptionBookRecordingAgentListCell extends AbstractToStringListCell<PrescriptionBookRecordingAgent>
{

   @Override
   protected String getToString(PrescriptionBookRecordingAgent item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
