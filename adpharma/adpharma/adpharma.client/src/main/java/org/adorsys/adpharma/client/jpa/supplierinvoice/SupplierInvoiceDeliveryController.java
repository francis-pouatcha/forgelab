package org.adorsys.adpharma.client.jpa.supplierinvoice;

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

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySearchInput;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySearchResult;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySearchService;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;

public abstract class SupplierInvoiceDeliveryController
{
   @Inject
   private DeliverySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private DeliverySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Delivery.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected SupplierInvoice sourceEntity;

   protected void disableButton(final SupplierInvoiceDeliverySelection selection)
   {
      selection.getDelivery().setDisable(true);
   }

   protected void activateButton(final SupplierInvoiceDeliverySelection selection)
   {
   }

   protected void bind(final SupplierInvoiceDeliverySelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            DeliverySearchService s = (DeliverySearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Delivery> entities = targetSearchResult.getResultList();
            selection.getDelivery().getItems().clear();
            selection.getDelivery().getItems().addAll(entities);
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

      selection.getDelivery().valueProperty().addListener(new ChangeListener<Delivery>()
      {
         @Override
         public void changed(ObservableValue<? extends Delivery> ov, Delivery oldValue,
               Delivery newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setDelivery(new SupplierInvoiceDelivery(newValue));
         }
      });

      selection.getDelivery().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new DeliverySearchInput()).start();
   }

}
