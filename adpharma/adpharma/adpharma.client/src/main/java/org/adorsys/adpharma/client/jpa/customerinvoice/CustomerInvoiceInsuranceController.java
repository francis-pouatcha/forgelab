package org.adorsys.adpharma.client.jpa.customerinvoice;

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

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchInput;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchResult;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceSearchService;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public abstract class CustomerInvoiceInsuranceController
{
   @Inject
   private InsurranceSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private InsurranceSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Insurrance.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected CustomerInvoice sourceEntity;

   protected void disableButton(final CustomerInvoiceInsuranceSelection selection)
   {
      selection.getInsurance().setDisable(true);
   }

   protected void activateButton(final CustomerInvoiceInsuranceSelection selection)
   {
   }

   protected void bind(final CustomerInvoiceInsuranceSelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            InsurranceSearchService s = (InsurranceSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Insurrance> entities = targetSearchResult.getResultList();
            selection.getInsurance().getItems().clear();
            selection.getInsurance().getItems().addAll(entities);
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

      selection.getInsurance().valueProperty().addListener(new ChangeListener<Insurrance>()
      {
         @Override
         public void changed(ObservableValue<? extends Insurrance> ov, Insurrance oldValue,
               Insurrance newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setInsurance(new CustomerInvoiceInsurance(newValue));
         }
      });

      selection.getInsurance().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new InsurranceSearchInput()).start();
   }

}
