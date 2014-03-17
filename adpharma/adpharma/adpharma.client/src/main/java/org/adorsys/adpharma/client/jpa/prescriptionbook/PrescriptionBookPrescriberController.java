package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import org.adorsys.adpharma.client.jpa.prescriber.PrescriberSearchInput;
import org.adorsys.adpharma.client.jpa.prescriber.PrescriberSearchResult;
import org.adorsys.adpharma.client.jpa.prescriber.PrescriberSearchService;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

public abstract class PrescriptionBookPrescriberController
{
   @Inject
   private PrescriberSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private PrescriberSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Prescriber.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected PrescriptionBook sourceEntity;

   protected void disableButton(final PrescriptionBookPrescriberSelection selection)
   {
      selection.getPrescriber().setDisable(true);
   }

   protected void activateButton(final PrescriptionBookPrescriberSelection selection)
   {
   }

   protected void bind(final PrescriptionBookPrescriberSelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            PrescriberSearchService s = (PrescriberSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Prescriber> entities = targetSearchResult.getResultList();
            selection.getPrescriber().getItems().clear();
            selection.getPrescriber().getItems().addAll(entities);
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

      selection.getPrescriber().valueProperty().addListener(new ChangeListener<Prescriber>()
      {
         @Override
         public void changed(ObservableValue<? extends Prescriber> ov, Prescriber oldValue,
               Prescriber newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setPrescriber(new PrescriptionBookPrescriber(newValue));
         }
      });

      selection.getPrescriber().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new PrescriberSearchInput()).start();
   }

}
