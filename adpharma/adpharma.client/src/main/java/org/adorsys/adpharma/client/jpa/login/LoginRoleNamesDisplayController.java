package org.adorsys.adpharma.client.jpa.login;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class LoginRoleNamesDisplayController extends LoginRoleNamesController
{

   @Inject
   private LoginDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent Login model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getLoginRoleNamesSelection(), displayView.getView().getLoginRoleNamesForm());
      bind(displayView.getView().getLoginRoleNamesSelection(), displayView.getView().getLoginRoleNamesForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent Login selectedEntity)
   {
      loadAssociation();
   }
}
