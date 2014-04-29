package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ModalPrescriptionBookHospitalCreateController extends PrescriptionBookHospitalController
{

   @Inject
   ModalPrescriptionBookCreateView modalcreateView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent PrescriptionBook model)
   {
      this.sourceEntity = model;
      bind(modalcreateView.getView().getPrescriptionBookHospitalSelection(), modalcreateView.getView().getPrescriptionBookHospitalForm());
      activateButton(modalcreateView.getView().getPrescriptionBookHospitalSelection());
   }
}
