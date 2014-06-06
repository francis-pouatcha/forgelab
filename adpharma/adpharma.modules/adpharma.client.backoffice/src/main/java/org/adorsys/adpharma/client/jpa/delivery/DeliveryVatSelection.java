package org.adorsys.adpharma.client.jpa.delivery;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DeliveryVatSelection extends AbstractSelection<Delivery, VAT>
{

   private ComboBox<DeliveryVat> vat;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      vat = viewBuilder.addComboBox("Delivery_vat_description.title", "vat", resourceBundle, false);

      vat.setCellFactory(new Callback<ListView<DeliveryVat>, ListCell<DeliveryVat>>()
      {
         @Override
         public ListCell<DeliveryVat> call(ListView<DeliveryVat> listView)
         {
            return new DeliveryVatListCell();
         }
      });
      vat.setButtonCell(new DeliveryVatListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
      vat.valueProperty().bindBidirectional(model.vatProperty());
   }

   public ComboBox<DeliveryVat> getVat()
   {
      return vat;
   }
}
