package org.adorsys.adpharma.client.jpa.customervoucher;

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

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSearchService;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;

public abstract class CustomerVoucherCustomerInvoiceController
{
   @Inject
   private CustomerInvoiceSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private CustomerInvoiceSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, CustomerInvoice.class, CustomerVoucher.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected CustomerVoucher sourceEntity;

   protected void disableButton(final CustomerVoucherCustomerInvoiceSelection selection)
   {
      selection.getCustomerInvoice().setDisable(true);
   }

   protected void activateButton(final CustomerVoucherCustomerInvoiceSelection selection)
   {
   }

   protected void bind(final CustomerVoucherCustomerInvoiceSelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            CustomerInvoiceSearchService s = (CustomerInvoiceSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<CustomerInvoice> entities = targetSearchResult.getResultList();
            selection.getCustomerInvoice().getItems().clear();
            selection.getCustomerInvoice().getItems().addAll(entities);
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

      selection.getCustomerInvoice().valueProperty().addListener(new ChangeListener<CustomerInvoice>()
      {
         @Override
         public void changed(ObservableValue<? extends CustomerInvoice> ov, CustomerInvoice oldValue,
               CustomerInvoice newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setCustomerInvoice(new CustomerVoucherCustomerInvoice(newValue));
         }
      });

      selection.getCustomerInvoice().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new CustomerInvoiceSearchInput()).start();
   }

}
