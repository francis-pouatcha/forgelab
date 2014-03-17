package org.adorsys.adpharma.client.jpa.procurementorder;

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

import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemRemoveService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

public abstract class ProcurementOrderProcurementOrderItemsController
{

   @Inject
   private ProcurementOrderItemSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   @Inject
   private ProcurementOrderItemRemoveService removeService;
   @Inject
   private ServiceCallFailedEventHandler removeServiceCallFailedEventHandler;

   @Inject
   @Bundle({ CrudKeys.class, ProcurementOrderItem.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog searchErrorMessageDialog;

   @Inject
   private ErrorMessageDialog removeErrorMessageDialog;

   @Inject
   private ConfirmDialog confirmDialog;

   protected ProcurementOrder sourceEntity;

   private ProcurementOrderItem selectedTarget;

   private ProcurementOrderItemSearchResult searchResult;

   protected void disableButton(final ProcurementOrderProcurementOrderItemsSelection selection, final ProcurementOrderProcurementOrderItemsForm form)
   {
      selection.getAddButton().setDisable(true);
      selection.getRemoveButton().setDisable(true);
   }

   protected void activateButton(final ProcurementOrderProcurementOrderItemsSelection selection, final ProcurementOrderProcurementOrderItemsForm form)
   {
   }

   protected void bind(final ProcurementOrderProcurementOrderItemsSelection selection, final ProcurementOrderProcurementOrderItemsForm form)
   {
      final TableView<ProcurementOrderItem> tableView = form.getDataList();
      final Pagination pagination = form.getPagination();
      tableView.itemsProperty().bind(sourceEntity.procurementOrderItemsProperty());

      selection.getAddButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (sourceEntity == null)
                     return;
                  // Map the bidirectional relationship if any
                  PropertyReader.copy(sourceEntity, selection.getTargetEntity().getProcurementOrder());
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
                  ProcurementOrderItem listIntem = new ProcurementOrderItem();
                  PropertyReader.copy(selection.getTargetEntity(), listIntem);
                  PropertyReader.copy(new ProcurementOrderItem(), selection.getTargetEntity());
                  sourceEntity.addToProcurementOrderItems(listIntem);
                  selection.closeDialog();
               }
            });

      selection.getCancelButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  PropertyReader.copy(new ProcurementOrderItem(), selection.getTargetEntity());
                  selection.closeDialog();
               }
            });
      selection.getRemoveButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  PropertyReader.copy(new ProcurementOrderItem(), selection.getTargetEntity());
                  if (selectedTarget != null)
                  {
                     if (selectedTarget.getId() != null)
                     {
                        confirmDialog.display();
                     }
                     else
                     {
                        // just remove from the list.
                        sourceEntity.getProcurementOrderItems().remove(selectedTarget);
                     }
                  }
                  selection.closeDialog();
               }
            });

      tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProcurementOrderItem>()
      {
         @Override
         public void changed(
               ObservableValue<? extends ProcurementOrderItem> property,
               ProcurementOrderItem oldValue, ProcurementOrderItem newValue)
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
            ProcurementOrderItemSearchService s = (ProcurementOrderItemSearchService) event.getSource();
            searchResult = s.getValue();
            event.consume();
            s.reset();
            List<ProcurementOrderItem> entities = searchResult.getResultList();
            sourceEntity.setProcurementOrderItems(entities);
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
               searchResult.setSearchInput(new ProcurementOrderItemSearchInput());
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
            + resourceBundle.getString("ProcurementOrderItem_description.title"));
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
            ProcurementOrderItemRemoveService service = (ProcurementOrderItemRemoveService) wse
                  .getSource();
            ProcurementOrderItem p = service.getValue();
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
      ProcurementOrderItemSearchInput searchInputs = new ProcurementOrderItemSearchInput();
      ProcurementOrderItem entity = new ProcurementOrderItem();
      entity.setProcurementOrder(new ProcurementOrderItemProcurementOrder(sourceEntity));
      searchInputs.getFieldNames().add("procurementOrder");
      searchInputs.setEntity(entity);
      searchService.setSearchInputs(searchInputs).start();
   }

   public boolean isAggregation()
   {
      return false;
   }
}
