package org.adorsys.adpharma.client.jpa.agency;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class AgencyCompanyEditController extends AgencyCompanyController
{

   @Inject
   AgencyEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Agency model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getAgencyCompanySelection());
      bind(editView.getView().getAgencyCompanySelection());
   }
}
