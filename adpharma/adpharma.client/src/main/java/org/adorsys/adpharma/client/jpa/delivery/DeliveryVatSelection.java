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

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;

public class DeliveryVatSelection extends AbstractSelection<Delivery, VAT>
{

   private ComboBox<VAT> vat;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      vat = viewBuilder.addComboBox("Delivery_vat_description.title", "vat", resourceBundle, false);

      vat.setCellFactory(new Callback<ListView<VAT>, ListCell<VAT>>()
      {
         @Override
         public ListCell<VAT> call(ListView<VAT> listView)
         {
            return new DeliveryVatListCell();
         }
      });
      vat.setButtonCell(new DeliveryVatListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
   }

   public ComboBox<VAT> getVat()
   {
      return vat;
   }
}