package org.adorsys.adpharma.client.jpa.supplierinvoice;

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
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;

public class SupplierInvoiceAgencySelection extends AbstractSelection<SupplierInvoice, Agency>
{

   private ComboBox<SupplierInvoiceAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("SupplierInvoice_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<SupplierInvoiceAgency>, ListCell<SupplierInvoiceAgency>>()
      {
         @Override
         public ListCell<SupplierInvoiceAgency> call(ListView<SupplierInvoiceAgency> listView)
         {
            return new SupplierInvoiceAgencyListCell();
         }
      });
      agency.setButtonCell(new SupplierInvoiceAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<SupplierInvoiceAgency> getAgency()
   {
      return agency;
   }
}
