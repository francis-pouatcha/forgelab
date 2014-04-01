package org.adorsys.adpharma.client.jpa.salesorder;

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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchInput;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchResult;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchService;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public abstract class SalesOrderAgencyController
{
   @Inject
   private AgencySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private AgencySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected SalesOrder sourceEntity;

   protected void disableButton(final SalesOrderAgencySelection selection)
   {
      selection.getAgency().setDisable(true);
   }

   protected void activateButton(final SalesOrderAgencySelection selection)
   {
   }

   protected void bind(final SalesOrderAgencySelection selection, final SalesOrderAgencyForm form)
   {

      //	    selection.getAgency().valueProperty().bindBidirectional(sourceEntity.agencyProperty());

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
            selection.getAgency().getItems().clear();
            selection.getAgency().getItems().add(new SalesOrderAgency());
            for (Agency entity : entities)
            {
               selection.getAgency().getItems().add(new SalesOrderAgency(entity));
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

      selection.getAgency().valueProperty().addListener(new ChangeListener<SalesOrderAgency>()
      {
         @Override
         public void changed(ObservableValue<? extends SalesOrderAgency> ov, SalesOrderAgency oldValue,
               SalesOrderAgency newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setAgency(newValue);
         }
      });

      selection.getAgency().armedProperty().addListener(new ChangeListener<Boolean>()
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
