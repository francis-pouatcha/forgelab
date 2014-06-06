package org.adorsys.adpharma.client.jpa.disbursement;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchInput;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchResult;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public class DisbursementCashDrawerController {
	@Inject
	private CashDrawerSearchService searchService;
	@Inject
	private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

	private CashDrawerSearchResult targetSearchResult;

	@Inject
	@Bundle({ CrudKeys.class, Disbursement.class, CashDrawer.class })
	private ResourceBundle resourceBundle;

	@Inject
	private ErrorMessageDialog errorMessageDialog;

	protected CashDrawer sourceEntity;

	protected void disableButton(final DisbursementCashDrawerSelection selection)
	{
		selection.getCashDrawer().setDisable(true);
	}

	protected void activateButton(final DisbursementCashDrawerSelection selection)
	{
	}

	protected void bind(final DisbursementCashDrawerSelection selection, final DisbursementCashDrawerForm form)
	{

		//	    selection.getCashDrawer().valueProperty().bindBidirectional(sourceEntity.CashDrawerProperty());

		// send search result event.
		searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
				{
			@Override
			public void handle(WorkerStateEvent event)
			{
				CashDrawerSearchService s = (CashDrawerSearchService) event
						.getSource();
				targetSearchResult = s.getValue();
				event.consume();
				s.reset();
				List<CashDrawer> entities = targetSearchResult.getResultList();
				selection.getCashDrawer().getItems().clear();
				selection.getCashDrawer().getItems().add(new DisbursementCashDrawer());
				for (CashDrawer entity : entities)
				{
					selection.getCashDrawer().getItems().add(new DisbursementCashDrawer(entity));
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

		selection.getCashDrawer().valueProperty().addListener(new ChangeListener<DisbursementCashDrawer>()
				{
			@Override
			public void changed(ObservableValue<? extends DisbursementCashDrawer> ov, DisbursementCashDrawer oldValue,
					DisbursementCashDrawer newValue)
			{
				if (sourceEntity != null)
					form.update(newValue);
				//                sourceEntity.setCashDrawer(newValue);
			}
				});

		selection.getCashDrawer().armedProperty().addListener(new ChangeListener<Boolean>()
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
		searchService.setSearchInputs(new CashDrawerSearchInput()).start();
	}
}
