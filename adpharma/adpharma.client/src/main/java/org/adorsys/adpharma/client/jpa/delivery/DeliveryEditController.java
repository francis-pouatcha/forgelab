package org.adorsys.adpharma.client.jpa.delivery;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencyEditService;
import org.adorsys.adpharma.client.jpa.agency.AgencyLoadService;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleCreateService;
import org.adorsys.adpharma.client.jpa.article.ModalArticleCreateController;
import org.adorsys.adpharma.client.jpa.article.ModalArticleSearchControler;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class DeliveryEditController implements EntityController
{

	@Inject
	private DeliveryEditView editView;

	@Inject
	private DeliveryEditService editService;
	@Inject
	private ServiceCallFailedEventHandler editServiceCallFailedEventHandler;

	
	@Inject
	private ServiceCallFailedEventHandler loadServiceCallFailedEventHandler;

	@Inject
	@EntitySearchRequestedEvent
	private Event<Delivery> searchRequestEvent;

	@Inject
	@EntitySelectionEvent
	private Event<Delivery> editedDoneEvent;

	@Inject
	private ErrorMessageDialog editErrorMessageDialog;

	@Inject
	@Bundle(CrudKeys.class)
	private ResourceBundle resourceBundle;

	Delivery editEntity;


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
	            	   searchRequestEvent.fire(editEntity);
	               }
	            });

	      // Save
	      editView.getSaveButton().setOnAction(
	            new EventHandler<ActionEvent>()
	            {
	               @Override
	               public void handle(ActionEvent e)
	               {

	                  Set<ConstraintViolation<Delivery>> violations = editView.getView().validate(editEntity);
	                  if (violations.isEmpty())
	                  {
	                     editService.setDelivery(editEntity).start();
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
	        	 DeliveryEditService s = (DeliveryEditService) event.getSource();
	            Delivery entity = s.getValue();
	            event.consume();
	            s.reset();
	            PropertyReader.copy(entity, editEntity);
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

	      editView.getView().addValidators();
	   }

	   @Override
	   public void display(Pane parent)
	   {
	      editView.getView().validate(editEntity);

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
	         @Observes @EntityEditRequestedEvent Delivery p)
	   {
	      PropertyReader.copy(p, editEntity);
	   }

	   /**
	    * This is the only time where the bind method is called on this object.
	    * @param model
	    */
	   public void handleNewModelEvent(@Observes @SelectedModelEvent Delivery model)
	   {
	      this.editEntity = model;
	      editView.bind(this.editEntity);
	   }

}