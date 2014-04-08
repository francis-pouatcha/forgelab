package org.adorsys.adpharma.client.jpa.cashout;

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

public class CashOutCashierSelection extends AbstractSelection<CashOut, Login>{
	private ComboBox<CashOutCashier> cashier;

	@Inject
	@Bundle({ CrudKeys.class, Login.class, CashOut.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();

		cashier = viewBuilder.addComboBox("CashOut_cashier_description.title", "cashier", resourceBundle, false);

		cashier.setCellFactory(new Callback<ListView<CashOutCashier>, ListCell<CashOutCashier>>()
				{
			@Override
			public ListCell<CashOutCashier> call(ListView<CashOutCashier> listView)
			{
				return new CashOutCashierListCell();
			}
				});
		cashier.setButtonCell(new CashOutCashierListCell());

		gridRows = viewBuilder.toRows();
	}

	public void bind(CashOut model)
	{
		cashier.valueProperty().bindBidirectional(model.cashierProperty());
	}

	public ComboBox<CashOutCashier> getCashier()
	{
		return cashier;
	}
}
