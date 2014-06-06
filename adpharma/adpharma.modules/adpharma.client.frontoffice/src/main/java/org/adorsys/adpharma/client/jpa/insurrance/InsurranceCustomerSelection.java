package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class InsurranceCustomerSelection extends AbstractSelection<Insurrance, Customer>
{

   private ComboBox<InsurranceCustomer> customer;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class, Insurrance.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      customer = viewBuilder.addComboBox("Insurrance_customer_description.title", "customer", resourceBundle, false);

      customer.setCellFactory(new Callback<ListView<InsurranceCustomer>, ListCell<InsurranceCustomer>>()
      {
         @Override
         public ListCell<InsurranceCustomer> call(ListView<InsurranceCustomer> listView)
         {
            return new InsurranceCustomerListCell();
         }
      });
      customer.setButtonCell(new InsurranceCustomerListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Insurrance model)
   {
      customer.valueProperty().bindBidirectional(model.customerProperty());
   }

   public ComboBox<InsurranceCustomer> getCustomer()
   {
      return customer;
   }
}
