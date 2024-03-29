package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class CashDrawerAgencySelection extends AbstractSelection<CashDrawer, Agency>
{

   private ComboBox<CashDrawerAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, CashDrawer.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("CashDrawer_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<CashDrawerAgency>, ListCell<CashDrawerAgency>>()
      {
         @Override
         public ListCell<CashDrawerAgency> call(ListView<CashDrawerAgency> listView)
         {
            return new CashDrawerAgencyListCell();
         }
      });
      agency.setButtonCell(new CashDrawerAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CashDrawer model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<CashDrawerAgency> getAgency()
   {
      return agency;
   }
}
