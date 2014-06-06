package org.adorsys.adpharma.client.jpa.payment;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PaymentCashierSelection extends AbstractSelection<Payment, Login>
{

   private ComboBox<PaymentCashier> cashier;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, Payment.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      cashier = viewBuilder.addComboBox("Payment_cashier_description.title", "cashier", resourceBundle, false);

      cashier.setCellFactory(new Callback<ListView<PaymentCashier>, ListCell<PaymentCashier>>()
      {
         @Override
         public ListCell<PaymentCashier> call(ListView<PaymentCashier> listView)
         {
            return new PaymentCashierListCell();
         }
      });
      cashier.setButtonCell(new PaymentCashierListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Payment model)
   {
      cashier.valueProperty().bindBidirectional(model.cashierProperty());
   }

   public ComboBox<PaymentCashier> getCashier()
   {
      return cashier;
   }
}
