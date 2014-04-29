package org.adorsys.adpharma.client.jpa.rolename;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.utils.PaginationUtils;
import org.adorsys.javafx.crud.extensions.view.ConfirmDialog;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

import org.adorsys.adpharma.client.jpa.permissionname.PermissionName;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionNameSearchInput;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionNameSearchResult;
import org.adorsys.adpharma.client.jpa.permissionname.PermissionNameSearchService;

import org.adorsys.adpharma.client.jpa.rolenamepermissionnameassoc.RoleNamePermissionNameAssoc;
import org.adorsys.adpharma.client.jpa.rolenamepermissionnameassoc.RoleNamePermissionNameAssocSearchInput;
import org.adorsys.adpharma.client.jpa.rolenamepermissionnameassoc.RoleNamePermissionNameAssocSearchResult;
import org.adorsys.adpharma.client.jpa.rolenamepermissionnameassoc.RoleNamePermissionNameAssocService;
import org.adorsys.adpharma.client.jpa.rolename.RoleName;

public abstract class RoleNamePermissionsController
{

   @Inject
   private PermissionNameSearchService targetSearchService;
   @Inject
   private ServiceCallFailedEventHandler targetSearchServiceCallFailedEventHandler;

   @Inject
   private ErrorMessageDialog targetSearchErrorMessageDialog;

   @Inject
   private ConfirmDialog confirmDialog;

   protected RoleName sourceEntity;

   private PermissionName selectedTarget;

   private RoleNamePermissionNameAssoc selectedAssoc;

   private PermissionNameSearchResult targetSearchResult;

   private RoleNamePermissionNameAssocSearchResult assocSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, PermissionName.class, RoleName.class })
   private ResourceBundle resourceBundle;

   @Inject
   private RoleNamePermissionNameAssocService assocLoadRemoteService;
   private AssocSearchAndLoadService assocLoadService;
   @Inject
   private ServiceCallFailedEventHandler assocLoadServiceCallFailedEventHandler;
   @Inject
   private ErrorMessageDialog assocLoadErrorMessageDialog;

   @Inject
   private RoleNamePermissionNameAssocService assocSearchRemoteService;
   private AssocSearchAndLoadService assocSearchService;
   @Inject
   private ServiceCallFailedEventHandler assocSearchServiceCallFailedEventHandler;
   @Inject
   private ErrorMessageDialog assocSearchErrorMessageDialog;

   @Inject
   private RoleNamePermissionNameAssocService assocRemoveRemoteService;
   private AssocRemoveService assocRemoveService;
   @Inject
   private ServiceCallFailedEventHandler assocRemoveServiceCallFailedEventHandler;
   @Inject
   private ErrorMessageDialog assocRemoveErrorMessageDialog;

   @Inject
   private RoleNamePermissionNameAssocService assocCreateRemoteService;
   private AssocCreateService assocCreateService;
   @Inject
   private ServiceCallFailedEventHandler assocCreateServiceCallFailedEventHandler;
   @Inject
   private ErrorMessageDialog assocCreateErrorMessageDialog;

   private Map<PermissionName, RoleNamePermissionNameAssoc> assocCache = new HashMap<PermissionName, RoleNamePermissionNameAssoc>();

   @PostConstruct
   protected void postConstruct()
   {
   }

   protected void disableButton(final RoleNamePermissionsSelection selection, final RoleNamePermissionsForm form)
   {
      selection.getSelectButton().setDisable(true);
   }

   protected void activateButton(final RoleNamePermissionsSelection selection, final RoleNamePermissionsForm form)
   {
   }

   protected void bind(final RoleNamePermissionsSelection selection, final RoleNamePermissionsForm form)
   {

      assocLoadService = new AssocSearchAndLoadService(assocLoadRemoteService);
      assocSearchService = new AssocSearchAndLoadService(assocSearchRemoteService);

      final TableView<PermissionName> formTableView = form.getDataList();
      final Pagination formPagination = form.getPagination();
      formTableView.itemsProperty().bind(selection.getAssocDataList().itemsProperty());
      formPagination.pageCountProperty().bindBidirectional(selection.getAssocPagination().pageCountProperty());
      formPagination.currentPageIndexProperty().bindBidirectional(selection.getAssocPagination().currentPageIndexProperty());

      assocRemoveService = new AssocRemoveService();

      assocCreateService = new AssocCreateService();

      selection.getSelectButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (sourceEntity == null)
                     return;
                  startTargetSearch();
                  startAssocSearch();
                  selection.display();
               }
            });

      selection.getCloseButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  selection.closeDialog();
               }
            });

      targetSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            PermissionNameSearchService s = (PermissionNameSearchService) event.getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<PermissionName> entities = targetSearchResult.getResultList();
            selection.getTargetDataList().getItems().clear();
            selection.getTargetDataList().getItems().addAll(entities);
            int maxResult = targetSearchResult.getSearchInput() != null ? targetSearchResult.getSearchInput().getMax() : 5;
            int pageCount = PaginationUtils.computePageCount(targetSearchResult.getCount(), maxResult);
            selection.getTargetPagination().setPageCount(pageCount);
            int firstResult = targetSearchResult.getSearchInput() != null ? targetSearchResult.getSearchInput().getStart() : 0;
            int pageIndex = PaginationUtils.computePageIndex(firstResult, targetSearchResult.getCount(), maxResult);
            selection.getTargetPagination().setCurrentPageIndex(pageIndex);
         }
      });

      targetSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            targetSearchErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Assoc_load_error.title"));
            if (!StringUtils.isBlank(message))
               targetSearchErrorMessageDialog.getDetailText().setText(message);
            targetSearchErrorMessageDialog.display();
         }
      });
      targetSearchService.setOnFailed(targetSearchServiceCallFailedEventHandler);

      targetSearchErrorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  targetSearchErrorMessageDialog.closeDialog();
               }
            });

      selection.getTargetDataList().getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<PermissionName>()
            {
               @Override
               public void changed(
                     ObservableValue<? extends PermissionName> property,
                     PermissionName oldValue, PermissionName newValue)
               {
                  if (newValue != null)
                     selectedTarget = newValue;
               }
            });

      selection.getTargetPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
      {
         @Override
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
         {
            if (targetSearchResult == null)
               return;
            if (targetSearchResult.getSearchInput() == null)
               targetSearchResult.setSearchInput(new PermissionNameSearchInput());
            int start = 0;
            int max = targetSearchResult.getSearchInput().getMax();
            if (newValue != null)
            {
               start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
            }
            targetSearchResult.getSearchInput().setStart(start);
            targetSearchService.setSearchInputs(targetSearchResult.getSearchInput()).start();
         }
      });

      /*
       * Confirm the selection and create the corresponding assoc entity. Put it in the assoc entity list.
       */
      selection.getAddButton().setOnAction(
            new EventHandler<ActionEvent>()
            {

               @Override
               public void handle(ActionEvent event)
               {
                  if (sourceEntity == null)
                     return;
                  if (selectedTarget == null)
                     return;
                  RoleNamePermissionNameAssocSearchInput searchInputs = new RoleNamePermissionNameAssocSearchInput();
                  RoleNamePermissionNameAssoc assoc = new RoleNamePermissionNameAssoc();
                  assoc.setSource(sourceEntity);
                  searchInputs.getFieldNames().add("source");
                  assoc.setTarget(selectedTarget);
                  searchInputs.getFieldNames().add("target");
                  assoc.setSourceQualifier("permissions");
                  searchInputs.getFieldNames().add("sourceQualifier");
                  assoc.setTargetQualifier("source");
                  searchInputs.getFieldNames().add("targetQualifier");
                  searchInputs.setEntity(assoc);
                  assocLoadService.setSearchInputs(searchInputs).start();
               }
            });

      assocLoadService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            AssocSearchAndLoadService s = (AssocSearchAndLoadService) event.getSource();
            RoleNamePermissionNameAssocSearchResult seekedAssocResult = s.getValue();
            event.consume();
            s.reset();
            if (seekedAssocResult != null && seekedAssocResult.getResultList() != null && !seekedAssocResult.getResultList().isEmpty())
            {// show error message assoc exists
               assocLoadErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Assoc_exists_error.title"));
               assocLoadErrorMessageDialog.getDetailText().setText(resourceBundle.getString("Assoc_exists_error.text"));
               assocLoadErrorMessageDialog.display();
            }
            else
            {
               RoleNamePermissionNameAssoc seekedAssoc = new RoleNamePermissionNameAssoc();
               seekedAssoc.setSource(sourceEntity);
               seekedAssoc.setSourceQualifier("permissions");
               seekedAssoc.setTarget(selectedTarget);
               seekedAssoc.setTargetQualifier("source");
               assocCreateService.setEntity(seekedAssoc).start();
            }
         }
      });

      assocLoadServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            assocLoadErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Assoc_load_error.title"));
            if (!StringUtils.isBlank(message))
               assocLoadErrorMessageDialog.getDetailText().setText(message);
            assocLoadErrorMessageDialog.display();
         }
      });
      assocLoadService.setOnFailed(assocLoadServiceCallFailedEventHandler);

      assocCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            AssocCreateService s = (AssocCreateService) event.getSource();
            selectedAssoc = s.getValue();
            event.consume();
            s.reset();
            // Add to the list of displayed association
            selection.getAssocDataList().getItems().add(selectedAssoc.getTarget());
            assocCache.put(selectedAssoc.getTarget(), selectedAssoc);
         }
      });
      assocCreateServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            assocCreateErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Assoc_create_error.title"));
            if (!StringUtils.isBlank(message))
               assocCreateErrorMessageDialog.getDetailText().setText(message);
            assocCreateErrorMessageDialog.display();
         }
      });
      assocCreateService.setOnFailed(assocCreateServiceCallFailedEventHandler);
      assocCreateErrorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  assocCreateErrorMessageDialog.closeDialog();
               }
            });

      assocSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            AssocSearchAndLoadService s = (AssocSearchAndLoadService) event.getSource();
            assocSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<RoleNamePermissionNameAssoc> entities = assocSearchResult.getResultList();
            selection.getAssocDataList().getItems().clear();
            for (RoleNamePermissionNameAssoc assoc : entities)
            {
               selection.getAssocDataList().getItems().add(assoc.getTarget());
               assocCache.put(assoc.getTarget(), assoc);
            }
            int maxResult = assocSearchResult.getSearchInput() != null ? assocSearchResult.getSearchInput().getMax() : 5;
            int pageCount = PaginationUtils.computePageCount(assocSearchResult.getCount(), maxResult);
            selection.getAssocPagination().setPageCount(pageCount);
            int firstResult = assocSearchResult.getSearchInput() != null ? assocSearchResult.getSearchInput().getStart() : 0;
            int pageIndex = PaginationUtils.computePageIndex(firstResult, assocSearchResult.getCount(), maxResult);
            selection.getAssocPagination().setCurrentPageIndex(pageIndex);
         }
      });

      assocSearchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            assocSearchErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Assoc_load_error.title"));
            if (!StringUtils.isBlank(message))
               assocSearchErrorMessageDialog.getDetailText().setText(message);
            assocSearchErrorMessageDialog.display();
         }
      });
      assocSearchService.setOnFailed(assocSearchServiceCallFailedEventHandler);

      assocSearchErrorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  assocSearchErrorMessageDialog.closeDialog();
               }
            });
      selection.getAssocDataList().getSelectionModel().selectedItemProperty()
            .addListener(new ChangeListener<PermissionName>()
            {
               @Override
               public void changed(
                     ObservableValue<? extends PermissionName> property,
                     PermissionName oldValue, PermissionName newValue)
               {
                  if (newValue != null)
                  {
                     if (assocCache.containsKey(newValue))
                     {
                        selectedAssoc = assocCache.get(newValue);
                     }
                  }
               }

            });

      selection.getAssocPagination().currentPageIndexProperty().addListener(new ChangeListener<Number>()
      {
         @Override
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
         {
            if (assocSearchResult == null)
               return;
            if (assocSearchResult.getSearchInput() == null)
               assocSearchResult.setSearchInput(new RoleNamePermissionNameAssocSearchInput());
            int start = 0;
            int max = assocSearchResult.getSearchInput().getMax();
            if (newValue != null)
            {
               start = new BigDecimal(newValue.intValue()).multiply(new BigDecimal(max)).intValue();
            }
            assocSearchResult.getSearchInput().setStart(start);
            assocSearchService.setSearchInputs(assocSearchResult.getSearchInput()).start();
         }
      });

      selection.getRemoveButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (selectedAssoc == null)
                     return;
                  confirmDialog.display();
               }
            });

      confirmDialog.setText(resourceBundle.getString("Entity_confirm_remove.title")
            + " "
            + resourceBundle.getString("PermissionName_description.title"));

      confirmDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  if (confirmDialog.hasDialog())
                  {
                     assocRemoveService.setEntity(selectedAssoc).start();
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

      assocRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            AssocRemoveService s = (AssocRemoveService) event.getSource();
            RoleNamePermissionNameAssoc removedAssoc = s.getValue();
            event.consume();
            s.reset();

            selection.getAssocDataList().getItems().remove(removedAssoc.getTarget());
            selectedAssoc = null;
         }
      });
      assocRemoveServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            assocRemoveErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Assoc_load_error.title"));
            if (!StringUtils.isBlank(message))
               assocRemoveErrorMessageDialog.getDetailText().setText(message);
            assocRemoveErrorMessageDialog.display();
         }
      });
      assocRemoveService.setOnFailed(assocRemoveServiceCallFailedEventHandler);

      assocRemoveErrorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  assocRemoveErrorMessageDialog.closeDialog();
               }
            });
   }

   protected void loadAssociation()
   {
      if (sourceEntity.getId() == null)
         return;
      startAssocSearch();
   }

   private void startAssocSearch()
   {
      if (sourceEntity == null || sourceEntity.getId() == null)
         return;

      RoleNamePermissionNameAssocSearchInput searchInputs = new RoleNamePermissionNameAssocSearchInput();
      RoleNamePermissionNameAssoc assoc = new RoleNamePermissionNameAssoc();

      assoc.setSource(sourceEntity);
      searchInputs.getFieldNames().add("source");
      assoc.setSourceQualifier("permissions");
      searchInputs.getFieldNames().add("sourceQualifier");
      assoc.setTargetQualifier("source");
      searchInputs.getFieldNames().add("targetQualifier");
      searchInputs.setEntity(assoc);
      assocCache.clear();
      assocSearchService.setSearchInputs(searchInputs).start();
   }

   private void startTargetSearch()
   {
      targetSearchService.setSearchInputs(new PermissionNameSearchInput()).start();
   }

   private final class AssocSearchAndLoadService extends
         Service<RoleNamePermissionNameAssocSearchResult>
   {
      private RoleNamePermissionNameAssocService remoteService;
      private RoleNamePermissionNameAssocSearchInput searchInputs;

      AssocSearchAndLoadService(final RoleNamePermissionNameAssocService remoteService)
      {
         super();
         this.remoteService = remoteService;
      }

      public AssocSearchAndLoadService setSearchInputs(
            RoleNamePermissionNameAssocSearchInput searchInputs)
      {
         this.searchInputs = searchInputs;
         return this;
      }

      @Override
      protected Task<RoleNamePermissionNameAssocSearchResult> createTask()
      {
         return new Task<RoleNamePermissionNameAssocSearchResult>()
         {
            @Override
            protected RoleNamePermissionNameAssocSearchResult call() throws Exception
            {
               if (searchInputs == null)
                  return null;
               return remoteService.findByLike(searchInputs);
            }
         };
      }
   }

   private final class AssocRemoveService extends Service<RoleNamePermissionNameAssoc>
   {

      private RoleNamePermissionNameAssoc entity;

      public AssocRemoveService setEntity(RoleNamePermissionNameAssoc entity)
      {
         this.entity = entity;
         return this;
      }

      @Override
      protected Task<RoleNamePermissionNameAssoc> createTask()
      {
         return new Task<RoleNamePermissionNameAssoc>()
         {
            @Override
            protected RoleNamePermissionNameAssoc call() throws Exception
            {
               return assocRemoveRemoteService.deleteById(entity
                     .getId());
            }
         };
      }
   }

   private final class AssocCreateService extends Service<RoleNamePermissionNameAssoc>
   {
      private RoleNamePermissionNameAssoc entity;

      public Service<RoleNamePermissionNameAssoc> setEntity(RoleNamePermissionNameAssoc entity)
      {
         this.entity = entity;
         return this;
      }

      @Override
      protected Task<RoleNamePermissionNameAssoc> createTask()
      {
         return new Task<RoleNamePermissionNameAssoc>()
         {
            @Override
            protected RoleNamePermissionNameAssoc call() throws Exception
            {
               return assocCreateRemoteService.create(entity);
            }
         };
      }
   };

   public boolean isAggregation()
   {
      return true;
   }
}
