package org.adorsys.adpharma.client.jpa.customer;

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

import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.employer.EmployerSearchInput;
import org.adorsys.adpharma.client.jpa.employer.EmployerSearchResult;
import org.adorsys.adpharma.client.jpa.employer.EmployerSearchService;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public abstract class CustomerEmployerController
{
   @Inject
   private EmployerSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private EmployerSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Employer.class, Customer.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Customer sourceEntity;

   protected void disableButton(final CustomerEmployerSelection selection)
   {
      selection.getEmployer().setDisable(true);
   }

   protected void activateButton(final CustomerEmployerSelection selection)
   {
   }

   protected void bind(final CustomerEmployerSelection selection, final CustomerEmployerForm form)
   {

      //	    selection.getEmployer().valueProperty().bindBidirectional(sourceEntity.employerProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            EmployerSearchService s = (EmployerSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Employer> entities = targetSearchResult.getResultList();
            selection.getEmployer().getItems().clear();
            selection.getEmployer().getItems().add(new CustomerEmployer());
            for (Employer entity : entities)
            {
               selection.getEmployer().getItems().add(new CustomerEmployer(entity));
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

      selection.getEmployer().valueProperty().addListener(new ChangeListener<CustomerEmployer>()
      {
         @Override
         public void changed(ObservableValue<? extends CustomerEmployer> ov, CustomerEmployer oldValue,
               CustomerEmployer newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setEmployer(newValue);
         }
      });

      selection.getEmployer().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new EmployerSearchInput()).start();
   }

}
