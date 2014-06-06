package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class StockMovementEditController implements EntityController
{

   @Inject
   private StockMovementEditView editView;

   @Inject
   private StockMovementEditService editService;
   @Inject
   private ServiceCallFailedEventHandler editServiceCallFailedEventHandler;

   @Inject
   private StockMovementLoadService loadService;
   @Inject
   private ServiceCallFailedEventHandler loadServiceCallFailedEventHandler;

   @Inject
   @EntityEditCanceledEvent
   private Event<StockMovement> editCanceledEvent;

   @Inject
   @EntityEditDoneEvent
   private Event<StockMovement> editedDoneEvent;

   private StockMovement displayedEntity;

   @Inject
   private ErrorMessageDialog editErrorMessageDialog;

   @Inject
   private ErrorMessageDialog loadErrorMessageDialog;

   @Inject
   @Bundle(CrudKeys.class)
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      // Reset
      editView.getCancelButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent e)
               {
                  loadService.setId(displayedEntity.getId()).start();
               }
            });

      // Save
      editView.getSaveButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent e)
               {

                  Set<ConstraintViolation<StockMovement>> violations = editView.getView().validate(displayedEntity);
                  if (violations.isEmpty())
                  {
                     editService.setStockMovement(displayedEntity).start();
                  }
                  else
                  {
                     editErrorMessageDialog.getTitleText().setText(
                           resourceBundle.getString("Entity_edit_error.title"));
                     editErrorMessageDialog.getDetailText().setText(resourceBundle.getString("Entity_click_to_see_error"));
                     editErrorMessageDialog.display();
                  }
               }
            });

      // send search result event.
      editService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            StockMovementEditService s = (StockMovementEditService) event.getSource();
            StockMovement entity = s.getValue();
            event.consume();
            s.reset();
            PropertyReader.copy(entity, displayedEntity);
            editedDoneEvent.fire(entity);
         }
      });

      editServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            editErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Entity_edit_error.title"));
            if (!StringUtils.isBlank(message))
               editErrorMessageDialog.getDetailText().setText(message);
            editErrorMessageDialog.display();
         }
      });
      editService.setOnFailed(editServiceCallFailedEventHandler);
      editErrorMessageDialog.getOkButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event)
         {
            editErrorMessageDialog.closeDialog();
         }
      });

      // Disable save button during creation
      editView.getSaveButton().disableProperty()
            .bind(editService.runningProperty());

      // Handle edit canceld, reloading entity
      loadService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            StockMovementLoadService s = (StockMovementLoadService) event.getSource();
            StockMovement entity = s.getValue();
            event.consume();
            s.reset();
            PropertyReader.copy(entity, displayedEntity);
            editCanceledEvent.fire(displayedEntity);
         }
      });

      loadServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            loadErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Entity_load_error.title"));
            if (!StringUtils.isBlank(message))
               loadErrorMessageDialog.getDetailText().setText(message);
            loadErrorMessageDialog.display();
         }
      });
      loadService.setOnFailed(loadServiceCallFailedEventHandler);
      loadErrorMessageDialog.getOkButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent event)
         {
            loadErrorMessageDialog.closeDialog();
            editCanceledEvent.fire(displayedEntity);
         }
      });

      editView.getView().addValidators();
   }

   @Override
   public void display(Pane parent)
   {
      editView.getView().validate(displayedEntity);

      AnchorPane rootPane = editView.getRootPane();
      ObservableList<Node> children = parent.getChildren();
      if (!children.contains(rootPane))
      {
         children.add(rootPane);
      }
   }

   @Override
   public ViewType getViewType()
   {
      return ViewType.EDIT;
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent StockMovement p)
   {
      PropertyReader.copy(p, displayedEntity);
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent StockMovement model)
   {
      this.displayedEntity = model;
      editView.bind(this.displayedEntity);
   }
   
	public void reset() {
	     PropertyReader.copy(new StockMovement(), displayedEntity);
	}


}