package org.adorsys.adpharma.client.jpa.cashout;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchResult;
import org.adorsys.adpharma.client.jpa.login.LoginSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public class CashOutCashierController {
	 @Inject
	   private LoginSearchService searchService;
	   @Inject
	   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

	   private LoginSearchResult targetSearchResult;

	   @Inject
	   @Bundle({ CrudKeys.class, CashOut.class, Login.class })
	   private ResourceBundle resourceBundle;

	   @Inject
	   private ErrorMessageDialog errorMessageDialog;

	   protected Login sourceEntity;

	   protected void disableButton(final CashOutCashierSelection selection)
	   {
	      selection.getCashier().setDisable(true);
	   }

	   protected void activateButton(final CashOutCashierSelection selection)
	   {
	   }

	   protected void bind(final CashOutCashierSelection selection, final CashOutCashierForm form)
	   {

	      //	    selection.getCashier().valueProperty().bindBidirectional(sourceEntity.CashierProperty());

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
	            selection.getCashier().getItems().clear();
	            selection.getCashier().getItems().add(new CashOutCashier());
	            for (Login entity : entities)
	            {
	               selection.getCashier().getItems().add(new CashOutCashier(entity));
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

	      selection.getCashier().valueProperty().addListener(new ChangeListener<CashOutCashier>()
	      {
	         @Override
	         public void changed(ObservableValue<? extends CashOutCashier> ov, CashOutCashier oldValue,
	        		 CashOutCashier newValue)
	         {
	            if (sourceEntity != null)
	               form.update(newValue);
	            //                sourceEntity.setCashier(newValue);
	         }
	      });

	      selection.getCashier().armedProperty().addListener(new ChangeListener<Boolean>()
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
