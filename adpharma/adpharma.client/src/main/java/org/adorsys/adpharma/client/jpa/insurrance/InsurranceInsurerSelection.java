package org.adorsys.adpharma.client.jpa.insurrance;

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

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;

public class InsurranceInsurerSelection extends AbstractSelection<Insurrance, Customer>
{

   private ComboBox<InsurranceInsurer> insurer;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class, Insurrance.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      insurer = viewBuilder.addComboBox("Insurrance_insurer_description.title", "insurer", resourceBundle, false);

      insurer.setCellFactory(new Callback<ListView<InsurranceInsurer>, ListCell<InsurranceInsurer>>()
      {
         @Override
         public ListCell<InsurranceInsurer> call(ListView<InsurranceInsurer> listView)
         {
            return new InsurranceInsurerListCell();
         }
      });
      insurer.setButtonCell(new InsurranceInsurerListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Insurrance model)
   {
      insurer.valueProperty().bindBidirectional(model.insurerProperty());
   }

   public ComboBox<InsurranceInsurer> getInsurer()
   {
      return insurer;
   }
}
