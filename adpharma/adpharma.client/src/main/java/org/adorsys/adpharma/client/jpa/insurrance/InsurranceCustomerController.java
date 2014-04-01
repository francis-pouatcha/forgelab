package org.adorsys.adpharma.client.jpa.insurrance;

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

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchResult;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchService;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;

public abstract class InsurranceCustomerController
{
   @Inject
   private CustomerSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private CustomerSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class, Insurrance.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Insurrance sourceEntity;

   protected void disableButton(final InsurranceCustomerSelection selection)
   {
      selection.getCustomer().setDisable(true);
   }

   protected void activateButton(final InsurranceCustomerSelection selection)
   {
   }

   protected void bind(final InsurranceCustomerSelection selection, final InsurranceCustomerForm form)
   {

      //	    selection.getCustomer().valueProperty().bindBidirectional(sourceEntity.customerProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            CustomerSearchService s = (CustomerSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Customer> entities = targetSearchResult.getResultList();
            selection.getCustomer().getItems().clear();
            selection.getCustomer().getItems().add(new InsurranceCustomer());
            for (Customer entity : entities)
            {
               selection.getCustomer().getItems().add(new InsurranceCustomer(entity));
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

      selection.getCustomer().valueProperty().addListener(new ChangeListener<InsurranceCustomer>()
      {
         @Override
         public void changed(ObservableValue<? extends InsurranceCustomer> ov, InsurranceCustomer oldValue,
               InsurranceCustomer newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setCustomer(newValue);
         }
      });

      selection.getCustomer().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new CustomerSearchInput()).start();
   }

}
