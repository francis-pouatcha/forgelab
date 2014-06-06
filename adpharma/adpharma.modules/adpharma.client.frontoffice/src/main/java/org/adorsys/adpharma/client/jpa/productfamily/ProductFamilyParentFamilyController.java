package org.adorsys.adpharma.client.jpa.productfamily;

import java.math.BigDecimal;
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
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class ProductFamilyParentFamilyController
{

   @Inject
   private ProductFamilySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class, ProductFamily.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected ProductFamily sourceEntity;

   private ProductFamilySearchResult searchResult;

   protected void disableButton(final ProductFamilyParentFamilySelection selection, final ProductFamilyParentFamilyForm form)
   {
      selection.getSelectButton().setDisable(true);
   }

   protected void activateButton(final ProductFamilyParentFamilySelection selection, final ProductFamilyParentFamilyForm form)
   {
   }

   protected void bind(final ProductFamilyParentFamilySelection selection, final ProductFamilyParentFamilyForm form)
   {
      selection.getSelectButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (sourceEntity == null)
                     return;
                  searchService
                        .setSearchInputs(new ProductFamilySearchInput())
                        .start();
                  selection.display();
               }
            });

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            ProductFamilySearchService s = (ProductFamilySearchService) event.getSource();
            searchResult = s.getValue();
            event.consume();
            s.reset();
            List<ProductFamily> entities = searchResult.getResultList();
            selection.getDataList().getItems().clear();
            selection.getDataList().getItems().addAll(entities);
            int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
            int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
            selection.getPagination().setPageCount(pageCount);
            int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
            int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
            selection.getPagination().setCurrentPageIndex(pageIndex);
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

      // Disable search button during search is running.
      selection.getSelectButton().disableProperty()
            .bind(searchService.runningProperty());

      /*
       * Disable cancel button and enable only if search is running.
       */
      selection.getCancelButton().setDisable(true);
      searchService.runningProperty().addListener(
            new ChangeListener<Boolean>()
            {
               @Override
               public void changed(
                     ObservableValue<? extends Boolean> arg0,
                     Boolean oldValue, Boolean newValue)
               {
                  selection.getCancelButton().setDisable(!newValue);
               }
            });
      selection.getCancelButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent e)
               {
                  if (searchService.isRunning())
                  {
                     searchService.cancel();
                     searchService.reset();
                  }
                  selection.closeDialog();
               }
            });
      selection.getCloseButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent e)
               {
                  if (searchService.isRunning())
                  {
                     searchService.cancel();
                     searchService.reset();
                  }
                  selection.closeDialog();
               }
            });

      selection.getDataList().getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<ProductFamily>()
            {
               @Override
               public void changed(
                     ObservableValue<? extends ProductFamily> property,
                     ProductFamily oldValue, ProductFamily newValue)
               {
                  if (newValue != null)
                     if (sourceEntity != null)
                        sourceEntity.setParentFamily(new ProductFamilyParentFamily(newValue));
                  selection.closeDialog();
               }
            });

      selection.getPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
      {
         @Override
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
         {
            if (searchResult == null)
               return;
            if (searchResult.getSearchInput() == null)
               searchResult.setSearchInput(new ProductFamilySearchInput());
            int start = 0;
            int max = searchResult.getSearchInput().getMax();
            if (newValue != null)
            {
               start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
            }
            searchResult.getSearchInput().setStart(start);
            searchService.setSearchInputs(searchResult.getSearchInput()).start();
         }
      });
   }

   protected void loadAssociation()
   {
   }
}
