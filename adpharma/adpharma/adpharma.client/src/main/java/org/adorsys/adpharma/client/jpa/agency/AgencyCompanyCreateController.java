package org.adorsys.adpharma.client.jpa.agency;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class AgencyCompanyCreateController extends AgencyCompanyController
{

   @Inject
   AgencyCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Agency model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getAgencyCompanySelection());
      activateButton(createView.getView().getAgencyCompanySelection());
   }
}
