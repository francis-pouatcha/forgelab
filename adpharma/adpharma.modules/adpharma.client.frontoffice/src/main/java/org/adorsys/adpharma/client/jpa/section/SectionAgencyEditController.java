package org.adorsys.adpharma.client.jpa.section;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class SectionAgencyEditController extends SectionAgencyController
{

   @Inject
   SectionEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent Section model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getSectionAgencySelection());
      bind(editView.getView().getSectionAgencySelection(), editView.getView().getSectionAgencyForm());
   }
}
