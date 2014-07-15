package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchResult;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class InsurranceInsurerController
{
	@Inject
	private CustomerSearchService searchService;
	@Inject
	private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

	private CustomerSearchResult targetSearchResult;

	@Inject
	@Bundle({ CrudKeys.class, Customer.class, Insurrance.class })
	private ResourceBundle resourceBundle;

	@Inject
	private ErrorMessageDialog errorMessageDialog;

	protected Insurrance sourceEntity;

	protected void disableButton(final InsurranceInsurerSelection selection)
	{
		selection.getInsurer().setDisable(true);
	}

	protected void activateButton(final InsurranceInsurerSelection selection)
	{
	}

	protected void bind(final InsurranceInsurerSelection selection, final InsurranceInsurerForm form)
	{

		//	    selection.getInsurer().valueProperty().bindBidirectional(sourceEntity.insurerProperty());

		// send search result event.
		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
				{
			@Override
			public void handle(WorkerStateEvent event)
			{
				CustomerSearchService s = (CustomerSearchService) event
						.getSource();
				targetSearchResult = s.getValue();
				event.consume();
				s.reset();
				List<Customer> entities = targetSearchResult.getResultList();
				entities.sort(new Comparator<Customer>() {

					@Override
					public int compare(Customer o1, Customer o2) {
//											o1.setFullName(o1.getFirstName());
//											o2.setFullName(o2.getFirstName());

						return o1.getFirstName().compareTo(o2.getFirstName());
					}
				});
				selection.getInsurer().getItems().clear();
				selection.getInsurer().getItems().add(new InsurranceInsurer());
				for (Customer entity : entities)
				{
					if("000000001".equals(entity.getSerialNumber()))
						continue ;
					selection.getInsurer().getItems().add(new InsurranceInsurer(entity));
				}
			}
				});
		searchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
		{
			@Override
			protected void showError(Throwable exception)
			{
				String message = exception.getMessage();
				errorMessageDialog.getTitleText().setText(
						resourceBundle.getString("Entity_search_error.title"));
				if (!StringUtils.isBlank(message))
					errorMessageDialog.getDetailText().setText(message);
				errorMessageDialog.display();
			}
		});
		searchService.setOnFailed(searchServiceCallFailedEventHandler);

		errorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>()
				{
					@Override
					public void handle(ActionEvent event)
					{
						errorMessageDialog.closeDialog();
					}
				});

		selection.getInsurer().valueProperty().addListener(new ChangeListener<InsurranceInsurer>()
				{
			@Override
			public void changed(ObservableValue<? extends InsurranceInsurer> ov, InsurranceInsurer oldValue,
					InsurranceInsurer newValue)
			{
				if (sourceEntity != null)
					form.update(newValue);
				//                sourceEntity.setInsurer(newValue);
			}
				});

		selection.getInsurer().armedProperty().addListener(new ChangeListener<Boolean>()
				{

			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue,
					Boolean oldValue, Boolean newValue)
			{
				if (newValue)
					load();
			}

				});
	}

	public void load()
	{
		searchService.setSearchInputs(new CustomerSearchInput()).start();
	}

}
