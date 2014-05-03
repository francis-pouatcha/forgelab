package org.adorsys.adpharma.client.jpa.customerinvoice;

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

import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemRemoveService;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.adorsys.javafx.crud.extensions.view.ConfirmDialog;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class CustomerInvoiceInvoiceItemsController
{

   @Inject
   private CustomerInvoiceItemSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   @Inject
   private CustomerInvoiceItemRemoveService removeService;
   @Inject
   private ServiceCallFailedEventHandler removeServiceCallFailedEventHandler;

   @Inject
   @Bundle({ CrudKeys.class, CustomerInvoiceItem.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog searchErrorMessageDialog;

   @Inject
   private ErrorMessageDialog removeErrorMessageDialog;

   @Inject
   private ConfirmDialog confirmDialog;

   protected CustomerInvoice sourceEntity;

   private CustomerInvoiceItem selectedTarget;

   private CustomerInvoiceItemSearchResult searchResult;

   protected void disableButton(final CustomerInvoiceInvoiceItemsSelection selection, final CustomerInvoiceInvoiceItemsForm form)
   {
      selection.getAddButton().setDisable(true);
      selection.getRemoveButton().setDisable(true);
   }

   protected void activateButton(final CustomerInvoiceInvoiceItemsSelection selection, final CustomerInvoiceInvoiceItemsForm form)
   {
   }

   protected void bind(final CustomerInvoiceInvoiceItemsSelection selection, final CustomerInvoiceInvoiceItemsForm form)
   {
      final TableView<CustomerInvoiceItem> tableView = form.getDataList();
      final Pagination pagination = form.getPagination();
      tableView.itemsProperty().bind(sourceEntity.invoiceItemsProperty());

      selection.getAddButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (sourceEntity == null)
                     return;
                  // Map the bidirectional relationship if any
                  PropertyReader.copy(sourceEntity, selection.getTargetEntity().getInvoice());
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
                  CustomerInvoiceItem listIntem = new CustomerInvoiceItem();
                  PropertyReader.copy(selection.getTargetEntity(), listIntem);
                  PropertyReader.copy(new CustomerInvoiceItem(), selection.getTargetEntity());
                  sourceEntity.addToInvoiceItems(listIntem);
                  selection.closeDialog();
               }
            });

      selection.getCancelButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  PropertyReader.copy(new CustomerInvoiceItem(), selection.getTargetEntity());
                  selection.closeDialog();
               }
            });
      selection.getRemoveButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  PropertyReader.copy(new CustomerInvoiceItem(), selection.getTargetEntity());
                  if (selectedTarget != null)
                  {
                     if (selectedTarget.getId() != null)
                     {
                        confirmDialog.display();
                     }
                     else
                     {
                        // just remove from the list.
                        sourceEntity.getInvoiceItems().remove(selectedTarget);
                     }
                  }
                  selection.closeDialog();
               }
            });

      tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerInvoiceItem>()
      {
         @Override
         public void changed(
               ObservableValue<? extends CustomerInvoiceItem> property,
               CustomerInvoiceItem oldValue, CustomerInvoiceItem newValue)
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
            CustomerInvoiceItemSearchService s = (CustomerInvoiceItemSearchService) event.getSource();
            searchResult = s.getValue();
            event.consume();
            s.reset();
            List<CustomerInvoiceItem> entities = searchResult.getResultList();
            sourceEntity.setInvoiceItems(entities);
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
               searchResult.setSearchInput(new CustomerInvoiceItemSearchInput());
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
            + resourceBundle.getString("CustomerInvoiceItem_description.title"));
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
            CustomerInvoiceItemRemoveService service = (CustomerInvoiceItemRemoveService) wse
                  .getSource();
            CustomerInvoiceItem p = service.getValue();
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
      CustomerInvoiceItemSearchInput searchInputs = new CustomerInvoiceItemSearchInput();
      CustomerInvoiceItem entity = new CustomerInvoiceItem();
      entity.setInvoice(new CustomerInvoiceItemInvoice(sourceEntity));
      searchInputs.getFieldNames().add("invoice");
      searchInputs.setEntity(entity);
      searchService.setSearchInputs(searchInputs).start();
   }

   public boolean isAggregation()
   {
      return false;
   }
}
