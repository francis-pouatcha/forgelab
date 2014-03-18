package org.adorsys.adpharma.client.jpa.hospital;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class HospitalIntialScreenController extends InitialScreenController<Hospital>
{
   @Override
   public Hospital newEntity()
   {
      return new Hospital();
   }
}
