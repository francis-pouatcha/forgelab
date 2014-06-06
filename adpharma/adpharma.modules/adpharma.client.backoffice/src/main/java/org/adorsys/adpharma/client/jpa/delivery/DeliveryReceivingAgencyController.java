package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchInput;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchResult;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class DeliveryReceivingAgencyController
{
   @Inject
   private AgencySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private AgencySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Delivery sourceEntity;

   protected void disableButton(final DeliveryReceivingAgencySelection selection)
   {
      selection.getReceivingAgency().setDisable(true);
   }

   protected void activateButton(final DeliveryReceivingAgencySelection selection)
   {
   }

   protected void bind(final DeliveryReceivingAgencySelection selection, final DeliveryReceivingAgencyForm form)
   {

      //	    selection.getReceivingAgency().valueProperty().bindBidirectional(sourceEntity.receivingAgencyProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            AgencySearchService s = (AgencySearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Agency> entities = targetSearchResult.getResultList();
            selection.getReceivingAgency().getItems().clear();
            selection.getReceivingAgency().getItems().add(new DeliveryReceivingAgency());
            for (Agency entity : entities)
            {
               selection.getReceivingAgency().getItems().add(new DeliveryReceivingAgency(entity));
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

      selection.getReceivingAgency().valueProperty().addListener(new ChangeListener<DeliveryReceivingAgency>()
      {
         @Override
         public void changed(ObservableValue<? extends DeliveryReceivingAgency> ov, DeliveryReceivingAgency oldValue,
               DeliveryReceivingAgency newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setReceivingAgency(newValue);
         }
      });

      selection.getReceivingAgency().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new AgencySearchInput()).start();
   }

}
