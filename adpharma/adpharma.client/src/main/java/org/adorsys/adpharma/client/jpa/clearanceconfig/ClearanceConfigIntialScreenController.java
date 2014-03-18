package org.adorsys.adpharma.client.jpa.clearanceconfig;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class ClearanceConfigIntialScreenController extends InitialScreenController<ClearanceConfig>
{
   @Override
   public ClearanceConfig newEntity()
   {
      return new ClearanceConfig();
   }
}
