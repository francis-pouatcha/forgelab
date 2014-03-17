package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.view.ConfirmDialog;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemRemoveService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSearchService;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItemSalesOrder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public abstract class SalesOrderSalesOrderItemsController
{

   @Inject
   private SalesOrderItemSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   @Inject
   private SalesOrderItemRemoveService removeService;
   @Inject
   private ServiceCallFailedEventHandler removeServiceCallFailedEventHandler;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrderItem.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog searchErrorMessageDialog;

   @Inject
   private ErrorMessageDialog removeErrorMessageDialog;

   @Inject
   private ConfirmDialog confirmDialog;

   protected SalesOrder sourceEntity;

   private SalesOrderItem selectedTarget;

   private SalesOrderItemSearchResult searchResult;

   protected void disableButton(final SalesOrderSalesOrderItemsSelection selection, final SalesOrderSalesOrderItemsForm form)
   {
      selection.getAddButton().setDisable(true);
      selection.getRemoveButton().setDisable(true);
   }

   protected void activateButton(final SalesOrderSalesOrderItemsSelection selection, final SalesOrderSalesOrderItemsForm form)
   {
   }

   protected void bind(final SalesOrderSalesOrderItemsSelection selection, final SalesOrderSalesOrderItemsForm form)
   {
      final TableView<SalesOrderItem> tableView = form.getDataList();
      final Pagination pagination = form.getPagination();
      tableView.itemsProperty().bind(sourceEntity.salesOrderItemsProperty());

      selection.getAddButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (sourceEntity == null)
                     return;
                  // Map the bidirectional relationship if any
                  PropertyReader.copy(sourceEntity, selection.getTargetEntity().getSalesOrder());
                  selection.display();
               }
            });

      selection.getConfirmButton().setOnAction(
            new EventHandler<ActionEvent>()
            {

               @Override
               public void handle(ActionEvent event)
               {
                  if (sourceEntity == null)
                     return;
                  SalesOrderItem listIntem = new SalesOrderItem();
                  PropertyReader.copy(selection.getTargetEntity(), listIntem);
                  PropertyReader.copy(new SalesOrderItem(), selection.getTargetEntity());
                  sourceEntity.addToSalesOrderItems(listIntem);
                  selection.closeDialog();
               }
            });

      selection.getCancelButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  PropertyReader.copy(new SalesOrderItem(), selection.getTargetEntity());
                  selection.closeDialog();
               }
            });
      selection.getRemoveButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  PropertyReader.copy(new SalesOrderItem(), selection.getTargetEntity());
                  if (selectedTarget != null)
                  {
                     if (selectedTarget.getId() != null)
                     {
                        confirmDialog.display();
                     }
                     else
                     {
                        // just remove from the list.
                        sourceEntity.getSalesOrderItems().remove(selectedTarget);
                     }
                  }
                  selection.closeDialog();
               }
            });

      tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SalesOrderItem>()
      {
         @Override
         public void changed(
               ObservableValue<? extends SalesOrderItem> property,
               SalesOrderItem oldValue, SalesOrderItem newValue)
         {
            if (newValue != null)
               selectedTarget = newValue;
         }
      });

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            SalesOrderItemSearchService s = (SalesOrderItemSearchService) event.getSource();
            searchResult = s.getValue();
            event.consume();
            s.reset();
            List<SalesOrderItem> entities = searchResult.getResultList();
            sourceEntity.setSalesOrderItems(entities);
            int maxResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getMax() : 5;
            int pageCount = PaginationUtils.computePageCount(searchResult.getCount(), maxResult);
            pagination.setPageCount(pageCount);
            int firstResult = searchResult.getSearchInput() != null ? searchResult.getSearchInput().getStart() : 0;
            int pageIndex = PaginationUtils.computePageIndex(firstResult, searchResult.getCount(), maxResult);
            pagination.setCurrentPageIndex(pageIndex);
         }
      });

      searchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            searchErrorMessageDialog.getTitleText().setText(
                  resourceBundle.getString("Entity_search_error.title"));
            String message = exception.getMessage();
            if (!StringUtils.isBlank(message))
               searchErrorMessageDialog.getDetailText().setText(message);
            searchErrorMessageDialog.display();
         }
      });
      searchService.setOnFailed(searchServiceCallFailedEventHandler);

      searchErrorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  searchErrorMessageDialog.closeDialog();
               }
            });

      // Disable search button during search is running.
      selection.getAddButton().disableProperty()
            .bind(searchService.runningProperty());

      pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>()
      {
         @Override
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
         {
            if (searchResult == null)
               return;
            if (searchService.isRunning())
               return;
            // TODO do this everywhere
            if (searchResult.getSearchInput() == null)
               searchResult.setSearchInput(new SalesOrderItemSearchInput());
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

      confirmDialog.setText(resourceBundle.getString("Entity_confirm_remove.title")
            + " "
            + resourceBundle.getString("SalesOrderItem_description.title"));
      confirmDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (confirmDialog.hasDialog())
                  {
                     // Delete the selected address and refresh the list.
                     removeService.setEntity(selectedTarget).start();
                     confirmDialog.closeDialog();
                  }
               }
            });

      confirmDialog.getCancelButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  confirmDialog.closeDialog();
               }
            });

      removeService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent wse)
         {
            SalesOrderItemRemoveService service = (SalesOrderItemRemoveService) wse
                  .getSource();
            SalesOrderItem p = service.getValue();
            wse.consume();
            service.reset();
            /*
             * Refres list from database.
             */
            searchService.setSearchInputs(searchResult.getSearchInput()).start();
         }
      });

      removeServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            removeErrorMessageDialog.getTitleText().setText(
                  resourceBundle.getString("Entity_remove_error.title"));
            String message = exception.getMessage();
            if (!StringUtils.isBlank(message))
               removeErrorMessageDialog.getDetailText().setText(message);
            removeErrorMessageDialog.display();
         }
      });
      removeService.setOnFailed(removeServiceCallFailedEventHandler);

      removeErrorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  removeErrorMessageDialog.closeDialog();
               }
            });
   }

   protected void loadAssociation()
   {
      if (sourceEntity.getId() == null)
         return;
      SalesOrderItemSearchInput searchInputs = new SalesOrderItemSearchInput();
      SalesOrderItem entity = new SalesOrderItem();
      entity.setSalesOrder(new SalesOrderItemSalesOrder(sourceEntity));
      searchInputs.getFieldNames().add("salesOrder");
      searchInputs.setEntity(entity);
      searchService.setSearchInputs(searchInputs).start();
   }

   public boolean isAggregation()
   {
      return false;
   }
}
