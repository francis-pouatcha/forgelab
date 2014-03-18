package org.adorsys.adpharma.client.jpa.agency;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class AgencyIntialScreenController extends InitialScreenController<Agency>
{
   @Override
   public Agency newEntity()
   {
      return new Agency();
   }
}
