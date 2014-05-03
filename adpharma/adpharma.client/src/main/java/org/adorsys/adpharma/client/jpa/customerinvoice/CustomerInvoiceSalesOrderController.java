package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchInput;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchResult;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class CustomerInvoiceSalesOrderController
{
   @Inject
   private SalesOrderSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private SalesOrderSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrder.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected CustomerInvoice sourceEntity;

   protected void disableButton(final CustomerInvoiceSalesOrderSelection selection)
   {
      selection.getSalesOrder().setDisable(true);
   }

   protected void activateButton(final CustomerInvoiceSalesOrderSelection selection)
   {
   }

   protected void bind(final CustomerInvoiceSalesOrderSelection selection, final CustomerInvoiceSalesOrderForm form)
   {

      //	    selection.getSalesOrder().valueProperty().bindBidirectional(sourceEntity.salesOrderProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            SalesOrderSearchService s = (SalesOrderSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<SalesOrder> entities = targetSearchResult.getResultList();
            selection.getSalesOrder().getItems().clear();
            selection.getSalesOrder().getItems().add(new CustomerInvoiceSalesOrder());
            for (SalesOrder entity : entities)
            {
               selection.getSalesOrder().getItems().add(new CustomerInvoiceSalesOrder(entity));
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

      selection.getSalesOrder().valueProperty().addListener(new ChangeListener<CustomerInvoiceSalesOrder>()
      {
         @Override
         public void changed(ObservableValue<? extends CustomerInvoiceSalesOrder> ov, CustomerInvoiceSalesOrder oldValue,
               CustomerInvoiceSalesOrder newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setSalesOrder(newValue);
         }
      });

      selection.getSalesOrder().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new SalesOrderSearchInput()).start();
   }

}
