package org.adorsys.adpharma.client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssoc;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssocSearchInput;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssocSearchResult;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssocService;

public class RolesTask extends Service<LoginRoleNameAssocSearchResult>
{

   @Inject
   private LoginRoleNameAssocService loginRoleNameAssocService;

   private Login login;
   
   public RolesTask setLogin(Login login)
   {
      this.login = login;
      return this;
   }

   @Override
   protected Task<LoginRoleNameAssocSearchResult> createTask()
   {
      return new Task<LoginRoleNameAssocSearchResult>()
      {
         @Override
         protected LoginRoleNameAssocSearchResult call() throws Exception
         {
			LoginRoleNameAssocSearchInput loginRoleNameAssocSearchInput = new LoginRoleNameAssocSearchInput();
			LoginRoleNameAssoc loginRoleNameAssoc = new LoginRoleNameAssoc();
			loginRoleNameAssoc.setSource(login);
			loginRoleNameAssoc.setSourceQualifier("roleNames");
			loginRoleNameAssocSearchInput.setEntity(loginRoleNameAssoc);
			loginRoleNameAssocSearchInput.getFieldNames().add("source");
			loginRoleNameAssocSearchInput.getFieldNames().add("sourceQualifier");
            return loginRoleNameAssocService.findBy(loginRoleNameAssocSearchInput);
         }
      };
   }
}
