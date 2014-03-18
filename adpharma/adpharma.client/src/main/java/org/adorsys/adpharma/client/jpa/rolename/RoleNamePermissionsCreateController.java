package org.adorsys.adpharma.client.jpa.rolename;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class RoleNamePermissionsCreateController extends RoleNamePermissionsController
{

   @Inject
   RoleNameCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent RoleName model)
   {
      this.sourceEntity = model;
      disableButton(createView.getView().getRoleNamePermissionsSelection(), createView.getView().getRoleNamePermissionsForm());
      bind(createView.getView().getRoleNamePermissionsSelection(), createView.getView().getRoleNamePermissionsForm());
   }
}
