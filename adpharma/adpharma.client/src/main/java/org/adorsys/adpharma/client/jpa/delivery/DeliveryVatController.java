package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.vat.VATSearchInput;
import org.adorsys.adpharma.client.jpa.vat.VATSearchResult;
import org.adorsys.adpharma.client.jpa.vat.VATSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class DeliveryVatController
{
   @Inject
   private VATSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private VATSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Delivery sourceEntity;

   protected void disableButton(final DeliveryVatSelection selection)
   {
      selection.getVat().setDisable(true);
   }

   protected void activateButton(final DeliveryVatSelection selection)
   {
   }

   protected void bind(final DeliveryVatSelection selection, final DeliveryVatForm form)
   {

      //	    selection.getVat().valueProperty().bindBidirectional(sourceEntity.vatProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            VATSearchService s = (VATSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<VAT> entities = targetSearchResult.getResultList();
            selection.getVat().getItems().clear();
            selection.getVat().getItems().add(new DeliveryVat());
            for (VAT entity : entities)
            {
               selection.getVat().getItems().add(new DeliveryVat(entity));
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

      selection.getVat().valueProperty().addListener(new ChangeListener<DeliveryVat>()
      {
         @Override
         public void changed(ObservableValue<? extends DeliveryVat> ov, DeliveryVat oldValue,
               DeliveryVat newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setVat(newValue);
         }
      });

      selection.getVat().armedProperty().addListener(new ChangeListener<Boolean>()
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
	   VATSearchInput vatSearchInput = new VATSearchInput();
	   vatSearchInput.setMax(-1);
      searchService.setSearchInputs(vatSearchInput).start();
   }

}
