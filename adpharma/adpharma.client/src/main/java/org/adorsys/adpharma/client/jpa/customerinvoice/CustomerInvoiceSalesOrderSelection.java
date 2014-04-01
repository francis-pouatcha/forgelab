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

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public class CustomerInvoiceSalesOrderSelection extends AbstractSelection<CustomerInvoice, SalesOrder>
{

   private ComboBox<CustomerInvoiceSalesOrder> salesOrder;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrder.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      salesOrder = viewBuilder.addComboBox("CustomerInvoice_salesOrder_description.title", "salesOrder", resourceBundle, false);

      salesOrder.setCellFactory(new Callback<ListView<CustomerInvoiceSalesOrder>, ListCell<CustomerInvoiceSalesOrder>>()
      {
         @Override
         public ListCell<CustomerInvoiceSalesOrder> call(ListView<CustomerInvoiceSalesOrder> listView)
         {
            return new CustomerInvoiceSalesOrderListCell();
         }
      });
      salesOrder.setButtonCell(new CustomerInvoiceSalesOrderListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerInvoice model)
   {
      salesOrder.valueProperty().bindBidirectional(model.salesOrderProperty());
   }

   public ComboBox<CustomerInvoiceSalesOrder> getSalesOrder()
   {
      return salesOrder;
   }
}
