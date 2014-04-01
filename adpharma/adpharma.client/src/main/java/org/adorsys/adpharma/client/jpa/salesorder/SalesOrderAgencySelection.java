package org.adorsys.adpharma.client.jpa.salesorder;

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
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public class SalesOrderAgencySelection extends AbstractSelection<SalesOrder, Agency>
{

   private ComboBox<SalesOrderAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("SalesOrder_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<SalesOrderAgency>, ListCell<SalesOrderAgency>>()
      {
         @Override
         public ListCell<SalesOrderAgency> call(ListView<SalesOrderAgency> listView)
         {
            return new SalesOrderAgencyListCell();
         }
      });
      agency.setButtonCell(new SalesOrderAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<SalesOrderAgency> getAgency()
   {
      return agency;
   }
}
