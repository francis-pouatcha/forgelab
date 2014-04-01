package org.adorsys.adpharma.client.jpa.delivery;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
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
	private DeliveryItemSearchService deliveryItemSearchService;

	@Inject
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler;

	@Inject
	@EntityEditRequestedEvent
	private Event<Delivery> editRequestEvent;
	
	@Inject
	@EntitySearchRequestedEvent
	private Event<Delivery> searchRequestEvent;


	@Inject
	@EntityRemoveRequestEvent
	private Event<Delivery> removeRequestEvent;

	@Inject 
	@ModalEntitySearchRequestedEvent
	private Event<ArticleSearchInput> modalArticleSearchEvent;

	@Inject
	@ModalEntityCreateRequestedEvent
	private Event<Article> modalArticleCreateRequestEvent;


	@Inject
	private Delivery displayedEntity;


	@Inject
	@Bundle(CrudKeys.class)
	private ResourceBundle resourceBundle;

	@Inject
	DeliveryItem deliveryItem;

	@Inject
	private DeliveryRegistration registration;

	@PostConstruct
	public void postConstruct()
	{
		displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		displayView.getDeleteButton().disableProperty().bind(registration.canEditProperty().not());

		displayView.bind(deliveryItem);
		displayView.bind(displayedEntity);

		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().title(resourceBundle.getString("Entity_create_error.title")).showException(exception);

			}
		});
		displayView.getAddArticleButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				modalArticleCreateRequestEvent.fire(new Article());

			}
		});

		displayView.getDataList().itemsProperty().getValue().addListener(new ListChangeListener<DeliveryItem>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends DeliveryItem> c) {
				BigDecimal processingAmount = displayView.getProcessAmont().getNumber();
				c.next();
				if(c.getAddedSize()!=0){
					List<? extends DeliveryItem> addedSubList = c.getAddedSubList();
					for (DeliveryItem item : addedSubList) {
						processingAmount = processingAmount.add(item.getTotalPurchasePrice());
						item.setDelivery(new DeliveryItemDelivery(displayedEntity));
						displayedEntity.deliveryItemsProperty().getValue().add(item);
					}
				}

				if(c.getRemovedSize()!=0){
					List<? extends DeliveryItem> removed = c.getRemoved();
					for (DeliveryItem item : removed) {
						processingAmount =processingAmount.subtract(item.getTotalPurchasePrice());
						displayedEntity.deliveryItemsProperty().getValue().remove(item);
					}
				}
				displayView.getProcessAmont().setNumber(processingAmount);

			}
		});

		/*
		 * listen to Ok button.
		 */
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

		/*
		 * listen to delete menu Item.
		 */

		displayView.getDeleteDeliveryMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DeliveryItem selectedItem = displayView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) displayView.getDataList().getItems().remove(selectedItem);

			}
		});

		/*
		 * listen to edit menu Item.
		 */
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

		/*
		 * listen to article name textfied.
		 */
		displayView.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String articleName = displayView.getArticleName().getText();
					if(StringUtils.isBlank(articleName)) return;
					Article entity = new Article();
					entity.setArticleName(articleName);
					ArticleSearchInput asi = new ArticleSearchInput();
					asi.setEntity(entity);
					asi.getFieldNames().add("articleName");
					modalArticleSearchEvent.fire(asi);
				}
			}
		});



		/*
		 * listen to cancel button .
		 */
		displayView.getCancelButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						searchRequestEvent.fire(displayedEntity);
					}
				});

		/*
		 * listen to delete button.
		 */
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

		/*
		 * listen to edit button .
		 */
		displayView.getEditButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						if(!displayedEntity.getDeliveryProcessingState().equals(DocumentProcessingState.CLOSED)){
							editRequestEvent.fire(displayedEntity);
						}else {
							Dialogs.create().nativeTitleBar().message(resourceBundle.getString("Entity_remove_error.title")).showError();
						}
					}
				});



		/*
		 * listen to save button .
		 */
		displayView.getSaveButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						handleDeliveryClosedEvent(displayedEntity);
					}
				});

		/*
		 *  send search result event.
		 */
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
				//				editedDoneEvent.fire(entity);
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
				deliveryItemSearchService.setSearchInputs(getDeliveryItemSearchInput(entity)).start();

			}
		});

		
		closeService.setOnFailed(serviceCallFailedEventHandler);
		editService.setOnFailed(serviceCallFailedEventHandler);
		deliveryItemSearchService.setOnFailed(serviceCallFailedEventHandler);
		
		deliveryItemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
				{
			@Override
			public void handle(WorkerStateEvent event)
			{
				DeliveryItemSearchService s = (DeliveryItemSearchService) event.getSource();
				DeliveryItemSearchResult deliveryItemSearchResult = s.getValue();
				event.consume();
				s.reset();
				List<DeliveryItem> resultList = deliveryItemSearchResult.getResultList();
				displayedEntity.setDeliveryItems(resultList);
			}
				});
		

		// Disable save button during creation
		displayView.getSaveButton().disableProperty()
		.bind(editService.runningProperty());

		

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
	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent Agency removedEntity)
	{
		displayView.getDataList().getItems().remove(removedEntity);
	}

	public void handleEditRequestEvent(
			@Observes @EntitySelectionEvent Delivery p)
	{

		deliveryItemSearchService.setSearchInputs(getDeliveryItemSearchInput(p)).start();
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

	public void handleCreateDoneEvent(@Observes @EntityCreateDoneEvent Delivery model)
	{
		handleNewModelEvent(model);

	}

	private void handleSelectedArticle(Article article) {
		DeliveryItem fromArticle = DeliveryItem.fromArticle(article);
		PropertyReader.copy(fromArticle, deliveryItem);
	}

	private void handleModalArticleCreateDone(@Observes @ModalEntityCreateDoneEvent Article article) {
		handleSelectedArticle(article);

	} 

	public void handleArticleSearchDone(@Observes @ModalEntitySearchDoneEvent Article article)
	{
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

	public DeliveryItemSearchInput getDeliveryItemSearchInput(Delivery delivery){
		DeliveryItemSearchInput deliveryItemSearchInput = new DeliveryItemSearchInput();
		deliveryItemSearchInput.getFieldNames().add("delivery");
		DeliveryItem deliveryItem2 = new DeliveryItem();
		deliveryItem2.setDelivery(new DeliveryItemDelivery(delivery));
		deliveryItemSearchInput.setEntity(deliveryItem2);
		return deliveryItemSearchInput;
	}
}