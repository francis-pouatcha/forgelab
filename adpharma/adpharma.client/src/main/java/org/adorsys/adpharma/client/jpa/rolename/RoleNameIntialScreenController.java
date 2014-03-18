package org.adorsys.adpharma.client.jpa.rolename;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class RoleNameIntialScreenController extends InitialScreenController<RoleName>
{
   @Override
   public RoleName newEntity()
   {
      return new RoleName();
   }
}
