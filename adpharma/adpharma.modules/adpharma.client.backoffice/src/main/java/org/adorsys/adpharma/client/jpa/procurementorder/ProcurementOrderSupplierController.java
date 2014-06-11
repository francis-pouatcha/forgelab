package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class ProcurementOrderSupplierController
{
   @Inject
   private SupplierSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private SupplierSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Supplier.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected ProcurementOrder sourceEntity;

   protected void disableButton(final ProcurementOrderSupplierSelection selection)
   {
      selection.getSupplier().setDisable(true);
   }

   protected void activateButton(final ProcurementOrderSupplierSelection selection)
   {
   }

   protected void bind(final ProcurementOrderSupplierSelection selection, final ProcurementOrderSupplierForm form)
   {

      //	    selection.getSupplier().valueProperty().bindBidirectional(sourceEntity.supplierProperty());

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
            selection.getSupplier().getItems().add(new ProcurementOrderSupplier());
            for (Supplier entity : entities)
            {
               selection.getSupplier().getItems().add(new ProcurementOrderSupplier(entity));
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

      selection.getSupplier().valueProperty().addListener(new ChangeListener<ProcurementOrderSupplier>()
      {
         @Override
         public void changed(ObservableValue<? extends ProcurementOrderSupplier> ov, ProcurementOrderSupplier oldValue,
               ProcurementOrderSupplier newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setSupplier(newValue);
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