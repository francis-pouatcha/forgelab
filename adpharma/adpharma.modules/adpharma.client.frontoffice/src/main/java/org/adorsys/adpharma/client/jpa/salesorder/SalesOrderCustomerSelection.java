package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.ResourceBundle;

import javafx.scene.control.Button;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SalesOrderCustomerSelection extends AbstractSelection<SalesOrder, Customer>
{

   private Button selectButton;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      selectButton = viewBuilder.addButton(
            "SalesOrder_customer_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getCustomer()
   {
      return selectButton; // select button required to mark invalid field.
   }
}
