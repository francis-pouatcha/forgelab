package org.adorsys.adpharma.client.jpa.section;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class SectionAgencyCreateController extends SectionAgencyController
{

   @Inject
   SectionCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Section model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getSectionAgencySelection(), createView.getView().getSectionAgencyForm());
      activateButton(createView.getView().getSectionAgencySelection());
   }
}
