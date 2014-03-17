package org.adorsys.adpharma.client.jpa.customer;

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

import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchInput;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchResult;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchService;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public abstract class CustomerCustomerCategoryController
{
   @Inject
   private CustomerCategorySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private CustomerCategorySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, CustomerCategory.class, Customer.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Customer sourceEntity;

   protected void disableButton(final CustomerCustomerCategorySelection selection)
   {
      selection.getCustomerCategory().setDisable(true);
   }

   protected void activateButton(final CustomerCustomerCategorySelection selection)
   {
   }

   protected void bind(final CustomerCustomerCategorySelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            CustomerCategorySearchService s = (CustomerCategorySearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<CustomerCategory> entities = targetSearchResult.getResultList();
            selection.getCustomerCategory().getItems().clear();
            selection.getCustomerCategory().getItems().addAll(entities);
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

      selection.getCustomerCategory().valueProperty().addListener(new ChangeListener<CustomerCategory>()
      {
         @Override
         public void changed(ObservableValue<? extends CustomerCategory> ov, CustomerCategory oldValue,
               CustomerCategory newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setCustomerCategory(new CustomerCustomerCategory(newValue));
         }
      });

      selection.getCustomerCategory().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new CustomerCategorySearchInput()).start();
   }

}
