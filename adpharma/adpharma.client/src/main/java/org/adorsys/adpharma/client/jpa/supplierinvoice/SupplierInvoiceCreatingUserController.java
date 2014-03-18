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

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchResult;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;

public abstract class SupplierInvoiceCreatingUserController
{
   @Inject
   private LoginSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private LoginSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected SupplierInvoice sourceEntity;

   protected void disableButton(final SupplierInvoiceCreatingUserSelection selection)
   {
      selection.getCreatingUser().setDisable(true);
   }

   protected void activateButton(final SupplierInvoiceCreatingUserSelection selection)
   {
   }

   protected void bind(final SupplierInvoiceCreatingUserSelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            LoginSearchService s = (LoginSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Login> entities = targetSearchResult.getResultList();
            selection.getCreatingUser().getItems().clear();
            selection.getCreatingUser().getItems().addAll(entities);
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

      selection.getCreatingUser().valueProperty().addListener(new ChangeListener<Login>()
      {
         @Override
         public void changed(ObservableValue<? extends Login> ov, Login oldValue,
               Login newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setCreatingUser(new SupplierInvoiceCreatingUser(newValue));
         }
      });

      selection.getCreatingUser().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new LoginSearchInput()).start();
   }

}