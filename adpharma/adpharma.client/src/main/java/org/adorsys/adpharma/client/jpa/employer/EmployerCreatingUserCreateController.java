package org.adorsys.adpharma.client.jpa.employer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class EmployerCreatingUserCreateController extends EmployerCreatingUserController
{

   @Inject
   EmployerCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Employer model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getEmployerCreatingUserSelection(), createView.getView().getEmployerCreatingUserForm());
      activateButton(createView.getView().getEmployerCreatingUserSelection());
   }
}
