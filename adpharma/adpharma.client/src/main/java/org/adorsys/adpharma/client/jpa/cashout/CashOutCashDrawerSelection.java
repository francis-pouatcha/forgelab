package org.adorsys.adpharma.client.jpa.cashout;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class CashOutCashDrawerSelection extends AbstractSelection<CashOut, CashDrawer>
{

	   private ComboBox<CashOutCashDrawer> cashDrawer;

	   @Inject
	   @Bundle({ CrudKeys.class, CashDrawer.class, CashOut.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();

	      cashDrawer = viewBuilder.addComboBox("CashOut_cashDrawer_description.title", "cashDrawer", resourceBundle, false);

	      cashDrawer.setCellFactory(new Callback<ListView<CashOutCashDrawer>, ListCell<CashOutCashDrawer>>()
	      {
	         @Override
	         public ListCell<CashOutCashDrawer> call(ListView<CashOutCashDrawer> listView)
	         {
	            return new CashOutCashDrawerListCell();
	         }
	      });
	      cashDrawer.setButtonCell(new CashOutCashDrawerListCell());

	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(CashOut model)
	   {
	      cashDrawer.valueProperty().bindBidirectional(model.casDrawerProperty());
	   }

	   public ComboBox<CashOutCashDrawer> getCashDrawer()
	   {
	      return cashDrawer;
	   }

}
