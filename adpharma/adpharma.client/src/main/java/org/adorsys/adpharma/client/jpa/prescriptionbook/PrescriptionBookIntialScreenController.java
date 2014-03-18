package org.adorsys.adpharma.client.jpa.prescriptionbook;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class PrescriptionBookIntialScreenController extends InitialScreenController<PrescriptionBook>
{
   @Override
   public PrescriptionBook newEntity()
   {
      return new PrescriptionBook();
   }
}
