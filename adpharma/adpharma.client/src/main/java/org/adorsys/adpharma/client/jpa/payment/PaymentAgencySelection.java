package org.adorsys.adpharma.client.jpa.payment;

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

public class PaymentAgencySelection extends AbstractSelection<Payment, Agency>
{

   private ComboBox<PaymentAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, Payment.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("Payment_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<PaymentAgency>, ListCell<PaymentAgency>>()
      {
         @Override
         public ListCell<PaymentAgency> call(ListView<PaymentAgency> listView)
         {
            return new PaymentAgencyListCell();
         }
      });
      agency.setButtonCell(new PaymentAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Payment model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<PaymentAgency> getAgency()
   {
      return agency;
   }
}
