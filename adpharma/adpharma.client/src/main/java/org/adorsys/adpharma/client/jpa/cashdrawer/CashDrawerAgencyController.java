package org.adorsys.adpharma.client.jpa.cashdrawer;

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
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public abstract class CashDrawerAgencyController
{
   @Inject
   private AgencySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private AgencySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, CashDrawer.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected CashDrawer sourceEntity;

   protected void disableButton(final CashDrawerAgencySelection selection)
   {
      selection.getAgency().setDisable(true);
   }

   protected void activateButton(final CashDrawerAgencySelection selection)
   {
   }

   protected void bind(final CashDrawerAgencySelection selection, final CashDrawerAgencyForm form)
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
            selection.getAgency().getItems().add(new CashDrawerAgency());
            for (Agency entity : entities)
            {
               selection.getAgency().getItems().add(new CashDrawerAgency(entity));
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

      selection.getAgency().valueProperty().addListener(new ChangeListener<CashDrawerAgency>()
      {
         @Override
         public void changed(ObservableValue<? extends CashDrawerAgency> ov, CashDrawerAgency oldValue,
               CashDrawerAgency newValue)
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
