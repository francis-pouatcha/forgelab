package org.adorsys.adpharma.client.jpa.payment;

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

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.payment.Payment;

public class PaymentCashDrawerSelection extends AbstractSelection<Payment, CashDrawer>
{

   private ComboBox<PaymentCashDrawer> cashDrawer;

   @Inject
   @Bundle({ CrudKeys.class, CashDrawer.class, Payment.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      cashDrawer = viewBuilder.addComboBox("Payment_cashDrawer_description.title", "cashDrawer", resourceBundle, false);

      cashDrawer.setCellFactory(new Callback<ListView<PaymentCashDrawer>, ListCell<PaymentCashDrawer>>()
      {
         @Override
         public ListCell<PaymentCashDrawer> call(ListView<PaymentCashDrawer> listView)
         {
            return new PaymentCashDrawerListCell();
         }
      });
      cashDrawer.setButtonCell(new PaymentCashDrawerListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Payment model)
   {
      cashDrawer.valueProperty().bindBidirectional(model.cashDrawerProperty());
   }

   public ComboBox<PaymentCashDrawer> getCashDrawer()
   {
      return cashDrawer;
   }
}
