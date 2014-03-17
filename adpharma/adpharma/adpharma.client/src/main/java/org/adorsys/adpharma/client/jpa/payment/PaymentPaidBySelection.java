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

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.payment.Payment;

public class PaymentPaidBySelection extends AbstractSelection<Payment, Customer>
{

   private ComboBox<Customer> paidBy;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class, Payment.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      paidBy = viewBuilder.addComboBox("Payment_paidBy_description.title", "paidBy", resourceBundle, false);

      paidBy.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>()
      {
         @Override
         public ListCell<Customer> call(ListView<Customer> listView)
         {
            return new PaymentPaidByListCell();
         }
      });
      paidBy.setButtonCell(new PaymentPaidByListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Payment model)
   {
   }

   public ComboBox<Customer> getPaidBy()
   {
      return paidBy;
   }
}
