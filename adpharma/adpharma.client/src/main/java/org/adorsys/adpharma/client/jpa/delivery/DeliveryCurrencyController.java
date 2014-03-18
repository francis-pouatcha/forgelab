package org.adorsys.adpharma.client.jpa.delivery;

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

import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.currency.CurrencySearchInput;
import org.adorsys.adpharma.client.jpa.currency.CurrencySearchResult;
import org.adorsys.adpharma.client.jpa.currency.CurrencySearchService;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;

public abstract class DeliveryCurrencyController
{
   @Inject
   private CurrencySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private CurrencySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Currency.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Delivery sourceEntity;

   protected void disableButton(final DeliveryCurrencySelection selection)
   {
      selection.getCurrency().setDisable(true);
   }

   protected void activateButton(final DeliveryCurrencySelection selection)
   {
   }

   protected void bind(final DeliveryCurrencySelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            CurrencySearchService s = (CurrencySearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Currency> entities = targetSearchResult.getResultList();
            selection.getCurrency().getItems().clear();
            selection.getCurrency().getItems().addAll(entities);
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

      selection.getCurrency().valueProperty().addListener(new ChangeListener<Currency>()
      {
         @Override
         public void changed(ObservableValue<? extends Currency> ov, Currency oldValue,
               Currency newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setCurrency(new DeliveryCurrency(newValue));
         }
      });

      selection.getCurrency().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new CurrencySearchInput()).start();
   }

}
