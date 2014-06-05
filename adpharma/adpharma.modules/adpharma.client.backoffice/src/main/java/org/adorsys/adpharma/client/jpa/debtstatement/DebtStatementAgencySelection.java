package org.adorsys.adpharma.client.jpa.debtstatement;

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

public class DebtStatementAgencySelection extends AbstractSelection<DebtStatement, Agency>
{

   private ComboBox<DebtStatementAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, DebtStatement.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("DebtStatement_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<DebtStatementAgency>, ListCell<DebtStatementAgency>>()
      {
         @Override
         public ListCell<DebtStatementAgency> call(ListView<DebtStatementAgency> listView)
         {
            return new DebtStatementAgencyListCell();
         }
      });
      agency.setButtonCell(new DebtStatementAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(DebtStatement model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<DebtStatementAgency> getAgency()
   {
      return agency;
   }
}
