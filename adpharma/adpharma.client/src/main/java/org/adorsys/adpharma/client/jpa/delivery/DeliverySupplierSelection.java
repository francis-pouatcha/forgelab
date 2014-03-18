package org.adorsys.adpharma.client.jpa.delivery;

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
import org.adorsys.adpharma.client.jpa.delivery.Delivery;

public class DeliverySupplierSelection extends AbstractSelection<Delivery, Supplier>
{

   private ComboBox<Supplier> supplier;

   @Inject
   @Bundle({ CrudKeys.class, Supplier.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      supplier = viewBuilder.addComboBox("Delivery_supplier_description.title", "supplier", resourceBundle, false);

      supplier.setCellFactory(new Callback<ListView<Supplier>, ListCell<Supplier>>()
      {
         @Override
         public ListCell<Supplier> call(ListView<Supplier> listView)
         {
            return new DeliverySupplierListCell();
         }
      });
      supplier.setButtonCell(new DeliverySupplierListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
   }

   public ComboBox<Supplier> getSupplier()
   {
      return supplier;
   }
}
