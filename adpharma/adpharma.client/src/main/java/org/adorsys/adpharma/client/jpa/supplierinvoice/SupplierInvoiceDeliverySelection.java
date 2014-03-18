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

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;

public class SupplierInvoiceDeliverySelection extends AbstractSelection<SupplierInvoice, Delivery>
{

   private ComboBox<Delivery> delivery;

   @Inject
   @Bundle({ CrudKeys.class, Delivery.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      delivery = viewBuilder.addComboBox("SupplierInvoice_delivery_description.title", "delivery", resourceBundle, false);

      delivery.setCellFactory(new Callback<ListView<Delivery>, ListCell<Delivery>>()
      {
         @Override
         public ListCell<Delivery> call(ListView<Delivery> listView)
         {
            return new SupplierInvoiceDeliveryListCell();
         }
      });
      delivery.setButtonCell(new SupplierInvoiceDeliveryListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
   }

   public ComboBox<Delivery> getDelivery()
   {
      return delivery;
   }
}