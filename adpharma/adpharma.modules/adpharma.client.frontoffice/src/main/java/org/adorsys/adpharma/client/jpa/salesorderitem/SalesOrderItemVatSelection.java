package org.adorsys.adpharma.client.jpa.salesorderitem;

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

public class SalesOrderItemVatSelection extends AbstractSelection<SalesOrderItem, VAT>
{

   private ComboBox<SalesOrderItemVat> vat;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class, SalesOrderItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      vat = viewBuilder.addComboBox("SalesOrderItem_vat_description.title", "vat", resourceBundle, false);

      vat.setCellFactory(new Callback<ListView<SalesOrderItemVat>, ListCell<SalesOrderItemVat>>()
      {
         @Override
         public ListCell<SalesOrderItemVat> call(ListView<SalesOrderItemVat> listView)
         {
            return new SalesOrderItemVatListCell();
         }
      });
      vat.setButtonCell(new SalesOrderItemVatListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrderItem model)
   {
      vat.valueProperty().bindBidirectional(model.vatProperty());
   }

   public ComboBox<SalesOrderItemVat> getVat()
   {
      return vat;
   }
}
