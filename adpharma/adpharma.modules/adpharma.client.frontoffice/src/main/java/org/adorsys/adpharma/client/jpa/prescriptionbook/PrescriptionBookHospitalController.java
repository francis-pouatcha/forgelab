package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.adpharma.client.jpa.hospital.HospitalSearchInput;
import org.adorsys.adpharma.client.jpa.hospital.HospitalSearchResult;
import org.adorsys.adpharma.client.jpa.hospital.HospitalSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class PrescriptionBookHospitalController
{
   @Inject
   private HospitalSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private HospitalSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Hospital.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected PrescriptionBook sourceEntity;

   protected void disableButton(final PrescriptionBookHospitalSelection selection)
   {
      selection.getHospital().setDisable(true);
   }

   protected void activateButton(final PrescriptionBookHospitalSelection selection)
   {
   }

   protected void bind(final PrescriptionBookHospitalSelection selection, final PrescriptionBookHospitalForm form)
   {

      //	    selection.getHospital().valueProperty().bindBidirectional(sourceEntity.hospitalProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            HospitalSearchService s = (HospitalSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Hospital> entities = targetSearchResult.getResultList();
            selection.getHospital().getItems().clear();
            selection.getHospital().getItems().add(new PrescriptionBookHospital());
            for (Hospital entity : entities)
            {
               selection.getHospital().getItems().add(new PrescriptionBookHospital(entity));
            }
         }
      });
      searchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            errorMessageDialog.getTitleText().setText(
                  resourceBundle.getString("Entity_search_error.title"));
            if (!StringUtils.isBlank(message))
               errorMessageDialog.getDetailText().setText(message);
            errorMessageDialog.display();
         }
      });
      searchService.setOnFailed(searchServiceCallFailedEventHandler);

      errorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  errorMessageDialog.closeDialog();
               }
            });

      selection.getHospital().valueProperty().addListener(new ChangeListener<PrescriptionBookHospital>()
      {
         @Override
         public void changed(ObservableValue<? extends PrescriptionBookHospital> ov, PrescriptionBookHospital oldValue,
               PrescriptionBookHospital newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setHospital(newValue);
         }
      });

      selection.getHospital().armedProperty().addListener(new ChangeListener<Boolean>()
      {

         @Override
         public void changed(ObservableValue<? extends Boolean> observableValue,
               Boolean oldValue, Boolean newValue)
         {
            if (newValue)
               load();
         }

      });
   }

   public void load()
   {
      searchService.setSearchInputs(new HospitalSearchInput()).start();
   }

}
