package org.adorsys.adpharma.client.jpa.disbursement;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DisbursementCashierSelection extends AbstractSelection<Disbursement, Login>{
	private ComboBox<DisbursementCashier> cashier;

	@Inject
	@Bundle({ CrudKeys.class, Login.class, Disbursement.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();

		cashier = viewBuilder.addComboBox("Disbursement_cashier_description.title", "cashier", resourceBundle, false);

		cashier.setCellFactory(new Callback<ListView<DisbursementCashier>, ListCell<DisbursementCashier>>()
				{
			@Override
			public ListCell<DisbursementCashier> call(ListView<DisbursementCashier> listView)
			{
				return new DisbursementCashierListCell();
			}
				});
		cashier.setButtonCell(new DisbursementCashierListCell());

		gridRows = viewBuilder.toRows();
	}

	public void bind(Disbursement model)
	{
		cashier.valueProperty().bindBidirectional(model.cashierProperty());
	}

	public ComboBox<DisbursementCashier> getCashier()
	{
		return cashier;
	}
}
