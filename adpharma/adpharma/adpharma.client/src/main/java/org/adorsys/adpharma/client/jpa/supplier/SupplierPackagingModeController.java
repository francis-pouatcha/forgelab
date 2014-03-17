package org.adorsys.adpharma.client.jpa.supplier;

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

import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchInput;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchResult;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchService;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public abstract class SupplierPackagingModeController
{
   @Inject
   private PackagingModeSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private PackagingModeSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, PackagingMode.class, Supplier.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Supplier sourceEntity;

   protected void disableButton(final SupplierPackagingModeSelection selection)
   {
      selection.getPackagingMode().setDisable(true);
   }

   protected void activateButton(final SupplierPackagingModeSelection selection)
   {
   }

   protected void bind(final SupplierPackagingModeSelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            PackagingModeSearchService s = (PackagingModeSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<PackagingMode> entities = targetSearchResult.getResultList();
            selection.getPackagingMode().getItems().clear();
            selection.getPackagingMode().getItems().addAll(entities);
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

      selection.getPackagingMode().valueProperty().addListener(new ChangeListener<PackagingMode>()
      {
         @Override
         public void changed(ObservableValue<? extends PackagingMode> ov, PackagingMode oldValue,
               PackagingMode newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setPackagingMode(new SupplierPackagingMode(newValue));
         }
      });

      selection.getPackagingMode().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new PackagingModeSearchInput()).start();
   }

}
