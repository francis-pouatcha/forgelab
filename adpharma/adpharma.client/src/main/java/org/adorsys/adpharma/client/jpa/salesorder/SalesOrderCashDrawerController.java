package org.adorsys.adpharma.client.jpa.salesorder;

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

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchInput;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchResult;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchService;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public abstract class SalesOrderCashDrawerController
{
   @Inject
   private CashDrawerSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private CashDrawerSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, CashDrawer.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected SalesOrder sourceEntity;

   protected void disableButton(final SalesOrderCashDrawerSelection selection)
   {
      selection.getCashDrawer().setDisable(true);
   }

   protected void activateButton(final SalesOrderCashDrawerSelection selection)
   {
   }

   protected void bind(final SalesOrderCashDrawerSelection selection, final SalesOrderCashDrawerForm form)
   {

      //	    selection.getCashDrawer().valueProperty().bindBidirectional(sourceEntity.cashDrawerProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            CashDrawerSearchService s = (CashDrawerSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<CashDrawer> entities = targetSearchResult.getResultList();
            selection.getCashDrawer().getItems().clear();
            selection.getCashDrawer().getItems().add(new SalesOrderCashDrawer());
            for (CashDrawer entity : entities)
            {
               selection.getCashDrawer().getItems().add(new SalesOrderCashDrawer(entity));
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

      selection.getCashDrawer().valueProperty().addListener(new ChangeListener<SalesOrderCashDrawer>()
      {
         @Override
         public void changed(ObservableValue<? extends SalesOrderCashDrawer> ov, SalesOrderCashDrawer oldValue,
               SalesOrderCashDrawer newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setCashDrawer(newValue);
         }
      });

      selection.getCashDrawer().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new CashDrawerSearchInput()).start();
   }

}
