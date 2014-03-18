package org.adorsys.adpharma.client.jpa.productfamily;

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

public abstract class ProductFamilyParentFamillyController
{
   @Inject
   private ProductFamilySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private ProductFamilySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class, ProductFamily.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected ProductFamily sourceEntity;

   protected void disableButton(final ProductFamilyParentFamillySelection selection)
   {
      selection.getParentFamilly().setDisable(true);
   }

   protected void activateButton(final ProductFamilyParentFamillySelection selection)
   {
   }

   protected void bind(final ProductFamilyParentFamillySelection selection)
   {

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            ProductFamilySearchService s = (ProductFamilySearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<ProductFamily> entities = targetSearchResult.getResultList();
            selection.getParentFamilly().getItems().clear();
            selection.getParentFamilly().getItems().addAll(entities);
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

      selection.getParentFamilly().valueProperty().addListener(new ChangeListener<ProductFamily>()
      {
         @Override
         public void changed(ObservableValue<? extends ProductFamily> ov, ProductFamily oldValue,
               ProductFamily newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setParentFamilly(new ProductFamilyParentFamilly(newValue));
         }
      });

      selection.getParentFamilly().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new ProductFamilySearchInput()).start();
   }

}
