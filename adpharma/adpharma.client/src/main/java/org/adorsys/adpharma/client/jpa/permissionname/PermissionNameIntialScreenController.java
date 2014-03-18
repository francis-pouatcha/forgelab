package org.adorsys.adpharma.client.jpa.permissionname;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class PermissionNameIntialScreenController extends InitialScreenController<PermissionName>
{
   @Override
   public PermissionName newEntity()
   {
      return new PermissionName();
   }
}
