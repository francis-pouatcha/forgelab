package org.adorsys.adpharma.client.jpa.insurrance;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class InsurranceIntialScreenController extends InitialScreenController<Insurrance>
{
   @Override
   public Insurrance newEntity()
   {
      return new Insurrance();
   }
}
