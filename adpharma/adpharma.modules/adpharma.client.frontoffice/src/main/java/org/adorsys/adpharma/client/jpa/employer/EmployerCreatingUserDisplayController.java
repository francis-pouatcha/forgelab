package org.adorsys.adpharma.client.jpa.employer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class EmployerCreatingUserDisplayController extends EmployerCreatingUserController
{

   @Inject
   private EmployerDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent Employer model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getEmployerCreatingUserSelection());
      bind(displayView.getView().getEmployerCreatingUserSelection(), displayView.getView().getEmployerCreatingUserForm());
   }
}
