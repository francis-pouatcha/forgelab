package org.adorsys.adpharma.client.jpa.disbursement;

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

public class DisbursementAgencySelection extends AbstractSelection<Disbursement, Agency>
{

	   private ComboBox<DisbursementAgency> agency;

	   @Inject
	   @Bundle({ CrudKeys.class, Agency.class, Disbursement.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();

	      agency = viewBuilder.addComboBox("Disbursement_agency_description.title", "agency", resourceBundle, false);

	      agency.setCellFactory(new Callback<ListView<DisbursementAgency>, ListCell<DisbursementAgency>>()
	      {
	         @Override
	         public ListCell<DisbursementAgency> call(ListView<DisbursementAgency> listView)
	         {
	            return new DisbursementAgencyListCell();
	         }
	      });
	      agency.setButtonCell(new DisbursementAgencyListCell());

	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(Disbursement model)
	   {
	      agency.valueProperty().bindBidirectional(model.agencyProperty());
	   }

	   public ComboBox<DisbursementAgency> getAgency()
	   {
	      return agency;
	   }

}
