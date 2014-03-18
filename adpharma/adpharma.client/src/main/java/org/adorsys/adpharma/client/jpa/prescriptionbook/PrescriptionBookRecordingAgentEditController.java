package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class PrescriptionBookRecordingAgentEditController extends PrescriptionBookRecordingAgentController
{

   @Inject
   PrescriptionBookEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent PrescriptionBook model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getPrescriptionBookRecordingAgentSelection());
      bind(editView.getView().getPrescriptionBookRecordingAgentSelection());
   }
}
