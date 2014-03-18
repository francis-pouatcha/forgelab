package org.adorsys.adpharma.client.jpa.employer;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class EmployerCreatingUserEditController extends EmployerCreatingUserController
{

   @Inject
   EmployerEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Employer model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getEmployerCreatingUserSelection());
      bind(editView.getView().getEmployerCreatingUserSelection());
   }
}
