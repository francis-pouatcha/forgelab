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

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public class SalesOrderCustomerSelection extends AbstractSelection<SalesOrder, Customer>
{

   private ComboBox<Customer> customer;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      customer = viewBuilder.addComboBox("SalesOrder_customer_description.title", "customer", resourceBundle, false);

      customer.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>()
      {
         @Override
         public ListCell<Customer> call(ListView<Customer> listView)
         {
            return new SalesOrderCustomerListCell();
         }
      });
      customer.setButtonCell(new SalesOrderCustomerListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
   }

   public ComboBox<Customer> getCustomer()
   {
      return customer;
   }
}
