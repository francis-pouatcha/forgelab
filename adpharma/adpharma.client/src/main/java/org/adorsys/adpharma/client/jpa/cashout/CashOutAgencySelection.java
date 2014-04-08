package org.adorsys.adpharma.client.jpa.cashout;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class CashOutAgencySelection extends AbstractSelection<CashOut, Agency>
{

	   private ComboBox<CashOutAgency> agency;

	   @Inject
	   @Bundle({ CrudKeys.class, Agency.class, CashOut.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();

	      agency = viewBuilder.addComboBox("CashOut_agency_description.title", "agency", resourceBundle, false);

	      agency.setCellFactory(new Callback<ListView<CashOutAgency>, ListCell<CashOutAgency>>()
	      {
	         @Override
	         public ListCell<CashOutAgency> call(ListView<CashOutAgency> listView)
	         {
	            return new CashOutAgencyListCell();
	         }
	      });
	      agency.setButtonCell(new CashOutAgencyListCell());

	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(CashOut model)
	   {
	      agency.valueProperty().bindBidirectional(model.agencyProperty());
	   }

	   public ComboBox<CashOutAgency> getAgency()
	   {
	      return agency;
	   }

}
