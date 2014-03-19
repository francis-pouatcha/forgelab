package org.adorsys.adpharma.client.jpa.delivery;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.binding.Bindings;
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
import org.adorsys.adpharma.client.jpa.article.ModalArticleCreateView;
import org.adorsys.adpharma.client.jpa.article.ModalArticleSearchControler;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemArticle;
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
public class DeliveryEditController implements EntityController
{

	@Inject
	private DeliveryEditView editView;

	@Inject
	private DeliveryEditService editService;
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
	@EntityEditDoneEvent
	private Event<Delivery> editedDoneEvent;

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
	ModalArticleCreateView modalArticleCreateView;

	@Inject
	Delivery model;

	@PostConstruct
	public void postConstruct()
	{
		editView.bind(deliveryItem);
		editView.bind(model);

		editView.getAddArticleButton().setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				modalArticleCreateView.showDiaLog();
				
			}
		});
		editView.getDataList().getItems().addListener(new ListChangeListener<DeliveryItem>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends DeliveryItem> c) {
				BigDecimal processingAmount = editView.getProcessAmont().getNumber();
				c.next();
				if(c.getAddedSize()!=0){
					List<? extends DeliveryItem> addedSubList = c.getAddedSubList();
					for (DeliveryItem deliveryItem : addedSubList) {
						processingAmount = processingAmount.add(deliveryItem.getTotalPurchasePrice());
					}
				}

				if(c.getRemovedSize()!=0){
					List<? extends DeliveryItem> removed = c.getRemoved();
					for (DeliveryItem deliveryItem : removed) {
						processingAmount =processingAmount.subtract(deliveryItem.getTotalPurchasePrice());

					}
				}
				editView.getProcessAmont().setNumber(processingAmount);

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
		editView.getPurchasePricePU().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					deliveryItem.calculateTotalAmout();
					DeliveryItem deliveryItem2 = new DeliveryItem();
					PropertyReader.copy(deliveryItem, deliveryItem2);
					editView.getDataList().getItems().add(deliveryItem2);
					PropertyReader.copy(new DeliveryItem(), deliveryItem);
				}
			}
		});

		editView.getDeleteDeliveryMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DeliveryItem selectedItem = editView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) editView.getDataList().getItems().remove(selectedItem);

			}
		});
		editView.getEditDeliveryMenu().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DeliveryItem selectedItem = editView.getDataList().getSelectionModel().getSelectedItem();
				if(selectedItem!=null) {
					PropertyReader.copy(selectedItem, deliveryItem);
					editView.getDataList().getItems().remove(selectedItem);
					editView.getStockQuantity().setFocusTraversable(true);
				}

			}
		});

		editView.getArticleName().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					String articleName = editView.getArticleName().getText();
					modalArticleSearchControler.handleArticleSearchInput(articleName);
				}
			}
		});

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

						Set<ConstraintViolation<Delivery>> violations = editView.getView().validate(displayedEntity);
						if (violations.isEmpty())
						{
							editService.setDelivery(displayedEntity).start();
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

		editView.getView().addValidators();
	}

	@Override
	public void display(Pane parent)
	{
		editView.getView().validate(displayedEntity);

		BorderPane rootPane = editView.getRootPane();
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
		PropertyReader.copy(p, displayedEntity);
	}

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent Delivery model)
	{
		this.displayedEntity = model;
		editView.bind(this.displayedEntity);

	}

	private void handleSelectedArticle(Article article) {
		DeliveryItem fromArticle = DeliveryItem.fromArticle(article);
		PropertyReader.copy(fromArticle, deliveryItem);
	}

}