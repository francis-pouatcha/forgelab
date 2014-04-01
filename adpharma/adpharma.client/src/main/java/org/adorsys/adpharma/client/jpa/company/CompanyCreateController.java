package org.adorsys.adpharma.client.jpa.company;

import java.util.HashSet;
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
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;
import org.adorsys.adpharma.client.jpa.company.Company;

@Singleton
public class CompanyCreateController implements EntityController
{

   @Inject
   private CompanyCreateView createView;

   @Inject
   private CompanyCreateService createService;
   @Inject
   private ServiceCallFailedEventHandler createServiceCallFailedEventHandler;

   @Inject
   @EntityCreateDoneEvent
   private Event<Company> createDoneEvent;

   @Inject
   @EntitySearchRequestedEvent
   private Event<Company> searchRequestedEvent;

   /**
    * The create page has it own model object.
    */
   Company model;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   @Inject
   @Bundle(CrudKeys.class)
   private ResourceBundle resourceBundle;

   /**
    * Do not try the put this widget in his parent component yet. We can not 
    * determine the order of post construct.
    * 
    * We will put the widget in the parent just before display occurs.
    */
   @PostConstruct
   public void init()
   {
      // Reset
      createView.getResetButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            PropertyReader.copy(new Company(), model);
         }
      });

      // Save
      createView.getSaveButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            Set<ConstraintViolation<Company>> violations = createView.getView().validate(model);
            if (violations.isEmpty())
            {
               createService.setModel(model).start();
            }
            else
            {
               errorMessageDialog.getTitleText().setText(
                     resourceBundle.getString("Entity_create_error.title"));
               errorMessageDialog.getDetailText().setText(resourceBundle.getString("Entity_click_to_see_error"));
               errorMessageDialog.display();
            }
         }
      });

      // send search result event.
      createService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            CompanyCreateService s = (CompanyCreateService) event.getSource();
            Company ent = s.getValue();
            event.consume();
            s.reset();
            createDoneEvent.fire(ent);
         }
      });
      createServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
      {
         @Override
         protected void showError(Throwable exception)
         {
            String message = exception.getMessage();
            errorMessageDialog.getTitleText().setText(
                  resourceBundle.getString("Entity_create_error.title"));
            if (!StringUtils.isBlank(message))
               errorMessageDialog.getDetailText().setText(message);
            errorMessageDialog.display();
         }
      });
      createService.setOnFailed(createServiceCallFailedEventHandler);
      errorMessageDialog.getOkButton().setOnAction(
            new EventHandler<ActionEvent>()
            {
               @Override
               public void handle(ActionEvent event)
               {
                  errorMessageDialog.closeDialog();
               }
            });

      // Disable save button during creation
      createView.getSaveButton().disableProperty().bind(createService.runningProperty());

      /*
       * listen to search button and fire search activated event.
       */
      createView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            searchRequestedEvent.fire(model);
         }
      });

      createView.getView().addValidators();
   }

   @Override
   public void display(Pane parent)
   {
      createView.getView().validate(model);

      AnchorPane rootPane = createView.getRootPane();
      ObservableList<Node> children = parent.getChildren();
      if (!children.contains(rootPane))
      {
         children.add(rootPane);
      }
   }

   @Override
   public ViewType getViewType()
   {
      return ViewType.CREATE;
   }

   /**
    * Cleanup search inputs.
    * 
    * @param null
    */
   public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent Company templateEntity)
   {
      PropertyReader.copy(templateEntity, model);
      // PropertyReader.cleanIds(model, new HashSet<Object>());
      model.cleanIds();
   }

   /**
    * Reset form on successful creation of the component.
    * 
    * @param createdEntity
    */
   public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Company createdEntity)
   {
      PropertyReader.copy(new Company(), model);
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent Company model)
   {
      this.model = model;
      createView.bind(this.model);
   }

}
