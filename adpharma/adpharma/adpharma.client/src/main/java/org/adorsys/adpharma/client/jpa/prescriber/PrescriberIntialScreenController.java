package org.adorsys.adpharma.client.jpa.prescriber;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class PrescriberIntialScreenController extends InitialScreenController<Prescriber>
{
   @Override
   public Prescriber newEntity()
   {
      return new Prescriber();
   }
}
