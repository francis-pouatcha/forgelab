package org.adorsys.adpharma.client.jpa.login;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class LoginAgencyEditController extends LoginAgencyController
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
      activateButton(editView.getView().getLoginAgencySelection());
      bind(editView.getView().getLoginAgencySelection(), editView.getView().getLoginAgencyForm());
   }
}
