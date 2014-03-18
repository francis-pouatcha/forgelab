package org.adorsys.adpharma.client.jpa.customerinvoice;

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

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public class CustomerInvoiceInsuranceSelection extends AbstractSelection<CustomerInvoice, Insurrance>
{

   private ComboBox<Insurrance> insurance;

   @Inject
   @Bundle({ CrudKeys.class, Insurrance.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      insurance = viewBuilder.addComboBox("CustomerInvoice_insurance_description.title", "insurance", resourceBundle, false);

      insurance.setCellFactory(new Callback<ListView<Insurrance>, ListCell<Insurrance>>()
      {
         @Override
         public ListCell<Insurrance> call(ListView<Insurrance> listView)
         {
            return new CustomerInvoiceInsuranceListCell();
         }
      });
      insurance.setButtonCell(new CustomerInvoiceInsuranceListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerInvoice model)
   {
   }

   public ComboBox<Insurrance> getInsurance()
   {
      return insurance;
   }
}
