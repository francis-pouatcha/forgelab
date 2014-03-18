package org.adorsys.adpharma.client.jpa.rolename;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class RoleNamePermissionsDisplayController extends RoleNamePermissionsController
{

   @Inject
   private RoleNameDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent RoleName model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getRoleNamePermissionsSelection(), displayView.getView().getRoleNamePermissionsForm());
      bind(displayView.getView().getRoleNamePermissionsSelection(), displayView.getView().getRoleNamePermissionsForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent RoleName selectedEntity)
   {
      loadAssociation();
   }
}
