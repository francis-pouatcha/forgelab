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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleCreateService;
import org.adorsys.adpharma.client.jpa.article.ModalArticleCreateController;
import org.adorsys.adpharma.client.jpa.article.ModalArticleSearchControler;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
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
public class DeliveryDisplayController implements EntityController
{

	@Inject
	private DeliveryDisplayView displayView;

	@Inject
	private DeliveryEditService editService;

	@Inject
	private DeliveryCloseService closeService;

	@Inject
	private ServiceCallFailedEventHandler editServiceCallFailedEventHandler;

	@Inject
	private DeliveryLoadService loadService;
	@Inject
	private ServiceCallFailedEventHandler loadServiceCallFailedEventHandler;

	@Inject
	@EntityEditCanceledEvent
	private Event<Delivery> editCanceledEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<Delivery> deliveryEditEvent;

	@Inject
	@EntityEditDoneEvent
	private Event<Delivery> editedDoneEvent;

	@Inject
	@EntityRemoveRequestEvent
	private Event<Delivery> removeRequestEvent;

	@Inject
	ArticleCreateService articleCreateService ;

	@Inject
	private ServiceCallFailedEventHandler createServiceCallFailedEventHandler;

	@Inject
	private Delivery displayedEntity;

	@Inject
	private ErrorMessageDialog editErrorMessageDialog;

	@Inject
	private ErrorMessageDialog loadErrorMessageDialog;

	@Inject
	@Bundle(CrudKeys.class)
	private ResourceBundle resourceBundle;


	@Inject
	ModalArticleSearchControler modalArticleSearchControler;

	@Inject
	DeliveryItem deliveryItem;

	@Inject
	ModalArticleCreateController modalArticleCreateController;

	@PostConstruct
	public void postConstruct()
	{
		displayView.bind(deliveryItem);
		displayView.bind(displayedEntity);

		createServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().title(resourceBundle.getString("Entity_create_error.title")).showException(exception);

			}
		});
		articleCreateService.setOnFailed(createServiceCallFailedEventHandler);

		displayView.getAddArticleButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				modalArticleCreateController.getModalArticleCreateView().showDiaLog();

			}
		});

		displayView.getDataList().getItems().addListener(new ListChangeListener<DeliveryItem>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends DeliveryItem> c) {
				BigDecimal processingAmount = displayView.getProcessAmont().getNumber();
				c.next();
				if(c.getAddedSize()!=0){
					List<? extends DeliveryItem> addedSubList = c.getAddedSubList();
					for (DeliveryItem deliveryItem : addedSubList) {
						processingAmount = processingAmount.add(deliveryItem.getTotalPurchasePrice());
						displayedEntity.getDeliveryItems().add(deliveryItem);
					}
				}

				if(c.getRemovedSize()!=0){
					List<? extends DeliveryItem> removed = c.getRemoved();
					for (DeliveryItem deliveryItem : removed) {
						processingAmount =processingAmount.subtract(deliveryItem.getTotalPurchasePrice());
						displayedEntity.getDeliveryItems().remove(deliveryItem);
					}
				}
				displayView.getProcessAmont().setNumber(processingAmount);

			}
		});

		modalArticleSearchControler.getView().getDataList().getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<Article>()
				{
			@Override
			public void changed(
					ObservableValue<? extends Article> property,
					Article oldValue, Article newValue)
			{
				if (newValue != null)
					handleSelectedArticle(newValue);
				modalArticleSearchControler.getView().closeDialog();
			}

				});
		displayView.getOkButton().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					handleAddDeliveryItem(deliveryItem);
				}
			}

		});

		displayView.getOkButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleAddDeliveryItem(deliveryItem);
			}

		});


		displayView.getDeleteDeliveryMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DeliveryItem selectedItem = displayView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) displayView.getDataList().getItems().remove(selectedItem);

			}
		});
		displayView.getEditDeliveryMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DeliveryItem selectedItem = displayView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					PropertyReader.copy(selectedItem, deliveryItem);
					displayView.getDataList().getItems().remove(selectedItem);
					displayView.getStockQuantity().setFocusTraversable(true);
				}

			}
		});

		displayView.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String articleName = displayView.getArticleName().getText();
					modalArticleSearchControler.handleArticleSearchInput(articleName);
				}
			}
		});

		// Reset
		displayView.getCancelButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						loadService.setId(displayedEntity.getId()).start();
					}
				});

		// Delete
		displayView.getDeleteButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						if(!displayedEntity.getDeliveryProcessingState().equals(DocumentProcessingState.CLOSED)){
							removeRequestEvent.fire(displayedEntity);
						}else {
							Dialogs.create().nativeTitleBar().message(resourceBundle.getString("Entity_remove_error.title")).showError();
						}
					}
				});

		// Edit
		displayView.getEditButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						//								if(!displayedEntity.getDeliveryProcessingState().equals(DocumentProcessingState.CLOSED)){
						//									removeRequestEvent.fire(displayedEntity);
						//								}else {
						//									Dialogs.create().nativeTitleBar().message(resourceBundle.getString("Entity_remove_error.title")).showError();
						//								}
						System.out.println("edit");
						deliveryEditEvent.fire(displayedEntity);
					}
				});



		// Save
		displayView.getSaveButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{

						handleDeliveryClosedEvent(displayedEntity);
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
				PropertyReader.copy(entity, displayedEntity);
				editedDoneEvent.fire(entity);
			}
				});

		closeService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryCloseService s = (DeliveryCloseService) event.getSource();
				Delivery entity = s.getValue();
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
//				String message = exception.getMessage();
//				editErrorMessageDialog.getTitleText().setText(resourceBundle.getString("Entity_edit_error.title"));
//				if (!StringUtils.isBlank(message))
//					editErrorMessageDialog.getDetailText().setText(message);
//				editErrorMessageDialog.display();
				Dialogs.create().nativeTitleBar().showException(exception);
			}
		});
		closeService.setOnFailed(editServiceCallFailedEventHandler);
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
		displayView.getSaveButton().disableProperty()
		.bind(editService.runningProperty());

		// Handle edit canceld, reloading entity
		loadService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
				{
			@Override
			public void handle(WorkerStateEvent event)
			{
				DeliveryLoadService s = (DeliveryLoadService) event.getSource();
				Delivery entity = s.getValue();
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

		displayView.getView().addValidators();
	}

	@Override
	public void display(Pane parent)
	{
		BorderPane rootPane = displayView.getRootPane();
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
			@Observes @EntitySelectionEvent Delivery p)
	{
		PropertyReader.copy(p, displayedEntity);
	}

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent Delivery model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);

	}

	private void handleSelectedArticle(Article article) {
		DeliveryItem fromArticle = DeliveryItem.fromArticle(article);
		PropertyReader.copy(fromArticle, deliveryItem);
	}

	private void handleModalArticleCreateDone(@Observes @EntityCreateDoneEvent Article article) {
		handleSelectedArticle(article);

	}

	private void handleAddDeliveryItem(DeliveryItem deliveryItem) {
		deliveryItem.calculateTotalAmout();
		DeliveryItem deliveryItem2 = new DeliveryItem();
		PropertyReader.copy(deliveryItem, deliveryItem2);
		displayView.getDataList().getItems().add(deliveryItem2);
		PropertyReader.copy(new DeliveryItem(), deliveryItem);

	}

	private void handleDeliveryClosedEvent(
			Delivery displayedEntity) {
		closeService.setDelivery(displayedEntity).start();

	}

}