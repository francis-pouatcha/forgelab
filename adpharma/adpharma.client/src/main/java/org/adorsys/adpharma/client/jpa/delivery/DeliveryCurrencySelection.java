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

import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;

public class DeliveryCurrencySelection extends AbstractSelection<Delivery, Currency>
{

   private ComboBox<DeliveryCurrency> currency;

   @Inject
   @Bundle({ CrudKeys.class, Currency.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      currency = viewBuilder.addComboBox("Delivery_currency_description.title", "currency", resourceBundle, false);

      currency.setCellFactory(new Callback<ListView<DeliveryCurrency>, ListCell<DeliveryCurrency>>()
      {
         @Override
         public ListCell<DeliveryCurrency> call(ListView<DeliveryCurrency> listView)
         {
            return new DeliveryCurrencyListCell();
         }
      });
      currency.setButtonCell(new DeliveryCurrencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
      currency.valueProperty().bindBidirectional(model.currencyProperty());
   }

   public ComboBox<DeliveryCurrency> getCurrency()
   {
      return currency;
   }
}
