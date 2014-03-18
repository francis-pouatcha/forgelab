package org.adorsys.adpharma.client.jpa.login;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class LoginRoleNamesEditController extends LoginRoleNamesController
{

   @Inject
   LoginEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Login model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getLoginRoleNamesSelection(), editView.getView().getLoginRoleNamesForm());
      bind(editView.getView().getLoginRoleNamesSelection(), editView.getView().getLoginRoleNamesForm());
   }

   public void handleEditRequestEvent(@Observes @EntityEditRequestedEvent Login p)
   {
      loadAssociation();
   }
}
