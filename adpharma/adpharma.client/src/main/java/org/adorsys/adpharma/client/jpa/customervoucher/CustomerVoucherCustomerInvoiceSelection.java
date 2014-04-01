package org.adorsys.adpharma.client.jpa.customervoucher;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;

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

   private Button selectButton;

   @Inject
   @Bundle({ CrudKeys.class, CustomerInvoice.class, CustomerVoucher.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      selectButton = viewBuilder.addButton(
            "CustomerVoucher_customerInvoice_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerVoucher model)
   {
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getCustomerInvoice()
   {
      return selectButton; // select button required to mark invalid field.
   }
}
