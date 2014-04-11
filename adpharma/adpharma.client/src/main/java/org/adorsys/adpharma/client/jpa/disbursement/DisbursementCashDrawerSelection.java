package org.adorsys.adpharma.client.jpa.disbursement;

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

public class DisbursementCashDrawerSelection extends AbstractSelection<Disbursement, CashDrawer>
{

	   private ComboBox<DisbursementCashDrawer> cashDrawer;

	   @Inject
	   @Bundle({ CrudKeys.class, CashDrawer.class, Disbursement.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();

	      cashDrawer = viewBuilder.addComboBox("Disbursement_cashDrawer_description.title", "cashDrawer", resourceBundle, false);

	      cashDrawer.setCellFactory(new Callback<ListView<DisbursementCashDrawer>, ListCell<DisbursementCashDrawer>>()
	      {
	         @Override
	         public ListCell<DisbursementCashDrawer> call(ListView<DisbursementCashDrawer> listView)
	         {
	            return new DisbursementCashDrawerListCell();
	         }
	      });
	      cashDrawer.setButtonCell(new DisbursementCashDrawerListCell());

	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(Disbursement model)
	   {
	      cashDrawer.valueProperty().bindBidirectional(model.cashDrawerProperty());
	   }

	   public ComboBox<DisbursementCashDrawer> getCashDrawer()
	   {
	      return cashDrawer;
	   }

}
