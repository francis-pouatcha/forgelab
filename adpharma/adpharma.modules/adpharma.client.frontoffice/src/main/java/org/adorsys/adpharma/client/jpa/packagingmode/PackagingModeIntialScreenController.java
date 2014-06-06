package org.adorsys.adpharma.client.jpa.packagingmode;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class PackagingModeIntialScreenController extends InitialScreenController<PackagingMode>
{
   @Override
   public PackagingMode newEntity()
   {
      return new PackagingMode();
   }
}
