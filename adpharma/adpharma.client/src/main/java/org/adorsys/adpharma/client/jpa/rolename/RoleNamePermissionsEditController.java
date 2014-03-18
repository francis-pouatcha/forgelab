package org.adorsys.adpharma.client.jpa.rolename;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class RoleNamePermissionsEditController extends RoleNamePermissionsController
{

   @Inject
   RoleNameEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent RoleName model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getRoleNamePermissionsSelection(), editView.getView().getRoleNamePermissionsForm());
      bind(editView.getView().getRoleNamePermissionsSelection(), editView.getView().getRoleNamePermissionsForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent RoleName p)
   {
      loadAssociation();
   }
}
