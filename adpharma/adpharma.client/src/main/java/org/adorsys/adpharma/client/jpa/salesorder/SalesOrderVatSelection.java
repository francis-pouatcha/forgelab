package org.adorsys.adpharma.client.jpa.salesorder;

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
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public class SalesOrderVatSelection extends AbstractSelection<SalesOrder, VAT>
{

   private ComboBox<SalesOrderVat> vat;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      vat = viewBuilder.addComboBox("SalesOrder_vat_description.title", "vat", resourceBundle, false);

      vat.setCellFactory(new Callback<ListView<SalesOrderVat>, ListCell<SalesOrderVat>>()
      {
         @Override
         public ListCell<SalesOrderVat> call(ListView<SalesOrderVat> listView)
         {
            return new SalesOrderVatListCell();
         }
      });
      vat.setButtonCell(new SalesOrderVatListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
      vat.valueProperty().bindBidirectional(model.vatProperty());
   }

   public ComboBox<SalesOrderVat> getVat()
   {
      return vat;
   }
}
