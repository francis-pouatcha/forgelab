package org.adorsys.adpharma.client.jpa.delivery;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

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

import org.adorsys.adpharma.client.events.DeliveryId;
import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemArticle;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemCreateService;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemEditService;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemRemoveService;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
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
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class DeliveryDisplayController implements EntityController
{

	@Inject
	private DeliveryDisplayView displayView;


	@Inject
	private DeliveryCloseService closeService;

	@Inject
	private DeliveryItemEditService deliveryItemEditService;

	@Inject
	private DeliveryItemRemoveService deliveryItemRemoveService;

	@Inject
	private DeliveryItemCreateService deliveryItemCreateService;

	@Inject
	private DeliveryItemSearchService deliveryItemSearchService;

	@Inject
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler;

	@Inject
	@EntityEditRequestedEvent
	private Event<Delivery> editRequestEvent;

	@Inject
	@EntityCreateRequestedEvent
	private Event<Delivery> createRequestEvent;

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
	
	@Inject
	@PrintRequestedEvent
	private Event<DeliveryId> printRequestedEvent;

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


		/*
		 * listen to Ok button.
		 */
		displayView.getOkButton().disableProperty().bind(deliveryItemCreateService.runningProperty());
		displayView.getOkButton().disableProperty().bind(deliveryItemEditService.runningProperty());
		displayView.getOkButton().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(isValidDeliveryItem())
					handleAddDeliveryItem(deliveryItem);
			}

		});

		displayView.getOkButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(isValidDeliveryItem())
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
				if(selectedItem!=null) {
					deliveryItemRemoveService.setEntity(selectedItem).start();
				}

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
						PropertyReader.copy(new Delivery(), displayedEntity);
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
						}
					}
				});
		/*
		 * listen to add button .
		 */
		displayView.getAddButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						createRequestEvent.fire(new Delivery());
					}
				});


		/*
		 * listen to save button .
		 */
		displayView.getSaveButton().disableProperty().bind(closeService.runningProperty());
		displayView.getSaveButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent e)
					{
						if(isValideDelivery()){
							BigDecimal processAmount = displayView.getProcessAmont().numberProperty().get();
							BigDecimal amountBeforeTax = displayedEntity.getAmountBeforeTax();
							if(amountBeforeTax.compareTo(processAmount)==0){
								handleDeliveryClosedEvent(displayedEntity);
							}else {
								Dialogs.create().message("le Montant Saisie dois etre egal au montant HT")
								.nativeTitleBar().showError();
							}
						}
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
				calculateProcessAmont();
			}
				});

		deliveryItemCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryItemCreateService s = (DeliveryItemCreateService) event.getSource();
				DeliveryItem createdItem = s.getValue();
				event.consume();
				s.reset();
				displayView.getDataList().getItems().add(createdItem);
				PropertyReader.copy(new DeliveryItem(), deliveryItem);
				calculateProcessAmont();

			}
		});
		deliveryItemCreateService.setOnFailed(serviceCallFailedEventHandler);

		deliveryItemEditService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryItemEditService s = (DeliveryItemEditService) event.getSource();
				DeliveryItem editedItem = s.getValue();
				event.consume();
				s.reset();
				displayView.getDataList().getItems().add(editedItem);
				PropertyReader.copy(new DeliveryItem(), deliveryItem);
				calculateProcessAmont();

			}
		});
		deliveryItemEditService.setOnFailed(serviceCallFailedEventHandler);

		deliveryItemRemoveService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryItemRemoveService s = (DeliveryItemRemoveService) event.getSource();
				DeliveryItem removeddItem = s.getValue();
				event.consume();
				s.reset();
				displayView.getDataList().getItems().remove(removeddItem);
				PropertyReader.copy(new DeliveryItem(), deliveryItem);
				calculateProcessAmont();

			}
		});
		deliveryItemRemoveService.setOnFailed(serviceCallFailedEventHandler);
		
		displayView.getPrintButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(displayedEntity==null || displayedEntity.getId()==null) return;
				printRequestedEvent.fire(new DeliveryId(displayedEntity.getId()));
			}
		});

		displayView.getView().addValidators();

		displayView.getDeliveryItemBar().visibleProperty().bind(displayedEntity.deliveryProcessingStateProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		displayView.getDeleteButton().visibleProperty().bind(displayedEntity.deliveryProcessingStateProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		displayView.getEditButton().visibleProperty().bind(displayedEntity.deliveryProcessingStateProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		displayView.getAddArticleButton().visibleProperty().bind(displayedEntity.deliveryProcessingStateProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		displayView.getSaveButton().visibleProperty().bind(displayedEntity.deliveryProcessingStateProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		displayView.getPrintButton().visibleProperty().bind(displayedEntity.deliveryProcessingStateProperty().isEqualTo(DocumentProcessingState.CLOSED));
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

	public boolean isValidDeliveryItem(){
		BigDecimal orderedQty = deliveryItem.getStockQuantity();
		DeliveryItemArticle article = deliveryItem.getArticle();
		if(article==null || article.getId()==null){
			Dialogs.create().nativeTitleBar().message("you need to specified article ").showError();
			return false;
		}
		if(orderedQty==null || orderedQty.compareTo(BigDecimal.ZERO)==0){
			Dialogs.create().nativeTitleBar().message("orderedQty is required ").showError();
			return false;
		}

		return true;

	}

	public boolean isValideDelivery(){
		if(displayedEntity.getDeliveryItems().isEmpty()){
			Dialogs.create().nativeTitleBar().message("DELIVERY  need to have at least one item").showError();
			return false;
		}
		return true;
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.EDIT;
	}
	public void handleRemovedEvent(@Observes @EntityRemoveDoneEvent DeliveryItem removedEntity)
	{
		displayView.getDataList().getItems().remove(removedEntity);
	}

	public void handleSelectedRequestEvent(
			@Observes @EntitySelectionEvent Delivery p)
	{

		PropertyReader.copy(p, displayedEntity);
		deliveryItemSearchService.setSearchInputs(getDeliveryItemSearchInput(p)).start();
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
		fromArticle.setDelivery(new DeliveryItemDelivery(displayedEntity));
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
		if(deliveryItem.getId()==null){
			deliveryItemCreateService.setModel(deliveryItem).start();
		}else {
			deliveryItemEditService.setDeliveryItem(deliveryItem).start();
		}
	}

	private void handleDeliveryClosedEvent(
			Delivery displayedEntity) {

		closeService.setDelivery(displayedEntity).start();

	}

	public void calculateProcessAmont(){
		List<DeliveryItem> deliveryItems = displayedEntity.getDeliveryItems();
		BigDecimal processAmount = BigDecimal.ZERO;
		for (DeliveryItem deliveryItem : deliveryItems) {
			processAmount=processAmount.add(deliveryItem.getTotalPurchasePrice());
		}
		displayView.getProcessAmont().setNumber(processAmount);
	}

	public DeliveryItemSearchInput getDeliveryItemSearchInput(Delivery delivery){
		DeliveryItemSearchInput deliveryItemSearchInput = new DeliveryItemSearchInput();
		deliveryItemSearchInput.getFieldNames().add("delivery");
		DeliveryItem deliveryItem2 = new DeliveryItem();
		deliveryItem2.setDelivery(new DeliveryItemDelivery(delivery));
		deliveryItemSearchInput.setEntity(deliveryItem2);
		return deliveryItemSearchInput;
	}
	public void reset() {
	     PropertyReader.copy(new Delivery(), displayedEntity);
	}
}