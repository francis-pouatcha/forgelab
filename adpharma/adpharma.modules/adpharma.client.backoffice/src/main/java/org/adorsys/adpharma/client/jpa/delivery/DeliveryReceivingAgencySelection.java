package org.adorsys.adpharma.client.jpa.delivery;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DeliveryReceivingAgencySelection extends AbstractSelection<Delivery, Agency>
{

   private ComboBox<DeliveryReceivingAgency> receivingAgency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      receivingAgency = viewBuilder.addComboBox("Delivery_receivingAgency_description.title", "receivingAgency", resourceBundle, false);

      receivingAgency.setCellFactory(new Callback<ListView<DeliveryReceivingAgency>, ListCell<DeliveryReceivingAgency>>()
      {
         @Override
         public ListCell<DeliveryReceivingAgency> call(ListView<DeliveryReceivingAgency> listView)
         {
            return new DeliveryReceivingAgencyListCell();
         }
      });
      receivingAgency.setButtonCell(new DeliveryReceivingAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
      receivingAgency.valueProperty().bindBidirectional(model.receivingAgencyProperty());
   }

   public ComboBox<DeliveryReceivingAgency> getReceivingAgency()
   {
      return receivingAgency;
   }
}
