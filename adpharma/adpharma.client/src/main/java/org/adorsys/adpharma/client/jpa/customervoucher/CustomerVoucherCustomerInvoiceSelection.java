package org.adorsys.adpharma.client.jpa.customervoucher;

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

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;

public class CustomerVoucherCustomerInvoiceSelection extends AbstractSelection<CustomerVoucher, CustomerInvoice>
{

   private ComboBox<CustomerInvoice> customerInvoice;

   @Inject
   @Bundle({ CrudKeys.class, CustomerInvoice.class, CustomerVoucher.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      customerInvoice = viewBuilder.addComboBox("CustomerVoucher_customerInvoice_description.title", "customerInvoice", resourceBundle, false);

      customerInvoice.setCellFactory(new Callback<ListView<CustomerInvoice>, ListCell<CustomerInvoice>>()
      {
         @Override
         public ListCell<CustomerInvoice> call(ListView<CustomerInvoice> listView)
         {
            return new CustomerVoucherCustomerInvoiceListCell();
         }
      });
      customerInvoice.setButtonCell(new CustomerVoucherCustomerInvoiceListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerVoucher model)
   {
   }

   public ComboBox<CustomerInvoice> getCustomerInvoice()
   {
      return customerInvoice;
   }
}
