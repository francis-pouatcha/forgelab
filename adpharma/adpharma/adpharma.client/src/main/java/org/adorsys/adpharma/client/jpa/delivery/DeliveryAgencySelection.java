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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;

public class DeliveryAgencySelection extends AbstractSelection<Delivery, Agency>
{

   private ComboBox<Agency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("Delivery_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<Agency>, ListCell<Agency>>()
      {
         @Override
         public ListCell<Agency> call(ListView<Agency> listView)
         {
            return new DeliveryAgencyListCell();
         }
      });
      agency.setButtonCell(new DeliveryAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
   }

   public ComboBox<Agency> getAgency()
   {
      return agency;
   }
}
