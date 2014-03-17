package org.adorsys.adpharma.client.jpa.customer;

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

import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public class CustomerCustomerCategorySelection extends AbstractSelection<Customer, CustomerCategory>
{

   private ComboBox<CustomerCategory> customerCategory;

   @Inject
   @Bundle({ CrudKeys.class, CustomerCategory.class, Customer.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      customerCategory = viewBuilder.addComboBox("Customer_customerCategory_description.title", "customerCategory", resourceBundle, false);

      customerCategory.setCellFactory(new Callback<ListView<CustomerCategory>, ListCell<CustomerCategory>>()
      {
         @Override
         public ListCell<CustomerCategory> call(ListView<CustomerCategory> listView)
         {
            return new CustomerCustomerCategoryListCell();
         }
      });
      customerCategory.setButtonCell(new CustomerCustomerCategoryListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Customer model)
   {
   }

   public ComboBox<CustomerCategory> getCustomerCategory()
   {
      return customerCategory;
   }
}
