package org.adorsys.adpharma.client.jpa.login;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class LoginIntialScreenController extends InitialScreenController<Login>
{
   @Override
   public Login newEntity()
   {
      return new Login();
   }
}
