package org.adorsys.adpharma.client.jpa.customerinvoice;

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

public class CustomerInvoiceAgencySelection extends AbstractSelection<CustomerInvoice, Agency>
{

   private ComboBox<CustomerInvoiceAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("CustomerInvoice_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<CustomerInvoiceAgency>, ListCell<CustomerInvoiceAgency>>()
      {
         @Override
         public ListCell<CustomerInvoiceAgency> call(ListView<CustomerInvoiceAgency> listView)
         {
            return new CustomerInvoiceAgencyListCell();
         }
      });
      agency.setButtonCell(new CustomerInvoiceAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerInvoice model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<CustomerInvoiceAgency> getAgency()
   {
      return agency;
   }
}
