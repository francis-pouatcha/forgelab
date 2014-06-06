package org.adorsys.adpharma.client.jpa.customervoucher;

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

public class CustomerVoucherAgencySelection extends AbstractSelection<CustomerVoucher, Agency>
{

   private ComboBox<CustomerVoucherAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, CustomerVoucher.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("CustomerVoucher_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<CustomerVoucherAgency>, ListCell<CustomerVoucherAgency>>()
      {
         @Override
         public ListCell<CustomerVoucherAgency> call(ListView<CustomerVoucherAgency> listView)
         {
            return new CustomerVoucherAgencyListCell();
         }
      });
      agency.setButtonCell(new CustomerVoucherAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerVoucher model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<CustomerVoucherAgency> getAgency()
   {
      return agency;
   }
}
