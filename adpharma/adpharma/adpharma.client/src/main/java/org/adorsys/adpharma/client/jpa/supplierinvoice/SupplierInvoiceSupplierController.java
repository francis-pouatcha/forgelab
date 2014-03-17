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

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;

public abstract class SupplierInvoiceSupplierController
{
   @Inject
   private SupplierSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private SupplierSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Supplier.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected SupplierInvoice sourceEntity;

   protected void disableButton(final SupplierInvoiceSupplierSelection selection)
   {
      selection.getSupplier().setDisable(true);
   }

   protected void activateButton(final SupplierInvoiceSupplierSelection selection)
   {
   }

   protected void bind(final SupplierInvoiceSupplierSelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            SupplierSearchService s = (SupplierSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Supplier> entities = targetSearchResult.getResultList();
            selection.getSupplier().getItems().clear();
            selection.getSupplier().getItems().addAll(entities);
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

      selection.getSupplier().valueProperty().addListener(new ChangeListener<Supplier>()
      {
         @Override
         public void changed(ObservableValue<? extends Supplier> ov, Supplier oldValue,
               Supplier newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setSupplier(new SupplierInvoiceSupplier(newValue));
         }
      });

      selection.getSupplier().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new SupplierSearchInput()).start();
   }

}
