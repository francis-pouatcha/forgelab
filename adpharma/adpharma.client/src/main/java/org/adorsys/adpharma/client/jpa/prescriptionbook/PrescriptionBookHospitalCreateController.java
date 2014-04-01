package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class PrescriptionBookHospitalCreateController extends PrescriptionBookHospitalController
{

   @Inject
   PrescriptionBookCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent PrescriptionBook model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getPrescriptionBookHospitalSelection(), createView.getView().getPrescriptionBookHospitalForm());
      activateButton(createView.getView().getPrescriptionBookHospitalSelection());
   }
}
