package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.ResourceBundle;

import javafx.scene.control.Button;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SupplierInvoiceSupplierSelection extends AbstractSelection<SupplierInvoice, Supplier>
{

   private Button selectButton;

   @Inject
   @Bundle({ CrudKeys.class, Supplier.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      selectButton = viewBuilder.addButton(
            "SupplierInvoice_supplier_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getSupplier()
   {
      return selectButton; // select button required to mark invalid field.
   }
}
