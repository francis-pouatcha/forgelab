package org.adorsys.adpharma.client.jpa.supplierinvoice;

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

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;

public class SupplierInvoiceSupplierSelection extends AbstractSelection<SupplierInvoice, Supplier>
{

   private ComboBox<Supplier> supplier;

   @Inject
   @Bundle({ CrudKeys.class, Supplier.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      supplier = viewBuilder.addComboBox("SupplierInvoice_supplier_description.title", "supplier", resourceBundle, false);

      supplier.setCellFactory(new Callback<ListView<Supplier>, ListCell<Supplier>>()
      {
         @Override
         public ListCell<Supplier> call(ListView<Supplier> listView)
         {
            return new SupplierInvoiceSupplierListCell();
         }
      });
      supplier.setButtonCell(new SupplierInvoiceSupplierListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
   }

   public ComboBox<Supplier> getSupplier()
   {
      return supplier;
   }
}
