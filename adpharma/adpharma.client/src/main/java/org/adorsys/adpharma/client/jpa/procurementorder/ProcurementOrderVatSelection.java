package org.adorsys.adpharma.client.jpa.procurementorder;

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
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

public class ProcurementOrderVatSelection extends AbstractSelection<ProcurementOrder, VAT>
{

   private ComboBox<ProcurementOrderVat> vat;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      vat = viewBuilder.addComboBox("ProcurementOrder_vat_description.title", "vat", resourceBundle, false);

      vat.setCellFactory(new Callback<ListView<ProcurementOrderVat>, ListCell<ProcurementOrderVat>>()
      {
         @Override
         public ListCell<ProcurementOrderVat> call(ListView<ProcurementOrderVat> listView)
         {
            return new ProcurementOrderVatListCell();
         }
      });
      vat.setButtonCell(new ProcurementOrderVatListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrder model)
   {
      vat.valueProperty().bindBidirectional(model.vatProperty());
   }

   public ComboBox<ProcurementOrderVat> getVat()
   {
      return vat;
   }
}
