package org.adorsys.adpharma.client.jpa.delivery;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class DeliveryCreateController implements EntityController
{

	@Inject
	private DeliveryCreateView createView;

	@Inject
	private DeliveryCreateService createService;
	@Inject
	private ServiceCallFailedEventHandler createServiceCallFailedEventHandler;

	@Inject
	@EntityCreateDoneEvent
	private Event<Delivery> createDoneEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<Delivery> searchRequestedEvent;

	@Inject
	@EntitySelectionEvent
	private Event<Delivery> selectionEvent;

	/**
	 * The create page has it own model object.
	 */
	Delivery model;

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
		createView.getView().getDeliveryVatSelection().getVat().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DeliveryVat>() {

			@Override
			public void changed(
					ObservableValue<? extends DeliveryVat> observable,
					DeliveryVat oldValue, DeliveryVat newValue) {
				if(newValue!=null){
					BigDecimal amountBeforeTax = model.getAmountBeforeTax()!=null?model.getAmountBeforeTax():BigDecimal.ZERO;
					model.setAmountVat(amountBeforeTax.multiply(VAT.getRawRate(newValue.getRate())));
				}

			}
		});
		// Reset
		createView.getResetButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				PropertyReader.copy(new Delivery(), model);
			}
				});

		// Save
		createView.getSaveButton().setOnAction(new EventHandler<ActionEvent>()
				{
			@Override
			public void handle(ActionEvent e)
			{
				model.calculateAmount();
				Set<ConstraintViolation<Delivery>> violations = createView.getView().validate(model);
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
				DeliveryCreateService s = (DeliveryCreateService) event.getSource();
				Delivery ent = s.getValue();
				event.consume();
				s.reset();
				createDoneEvent.fire(ent);
				selectionEvent.fire(ent);
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
	public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent Delivery templateEntity)
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
	public void handleCreatedEvent(@Observes @EntityCreateDoneEvent Delivery createdEntity)
	{
		PropertyReader.copy(new Delivery(), model);
	}

	public void handleNewModelEvent(@Observes @CreateModelEvent Delivery model)
	{
		this.model = model;
		createView.bind(this.model);
	}

}
