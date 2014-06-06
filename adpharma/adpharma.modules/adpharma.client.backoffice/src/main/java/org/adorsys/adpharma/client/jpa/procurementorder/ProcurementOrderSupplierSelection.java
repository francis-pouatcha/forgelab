package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProcurementOrderSupplierSelection extends AbstractSelection<ProcurementOrder, Supplier>
{

   private ComboBox<ProcurementOrderSupplier> supplier;

   @Inject
   @Bundle({ CrudKeys.class, Supplier.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      supplier = viewBuilder.addComboBox("ProcurementOrder_supplier_description.title", "supplier", resourceBundle, false);

      supplier.setCellFactory(new Callback<ListView<ProcurementOrderSupplier>, ListCell<ProcurementOrderSupplier>>()
      {
         @Override
         public ListCell<ProcurementOrderSupplier> call(ListView<ProcurementOrderSupplier> listView)
         {
            return new ProcurementOrderSupplierListCell();
         }
      });
      supplier.setButtonCell(new ProcurementOrderSupplierListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrder model)
   {
      supplier.valueProperty().bindBidirectional(model.supplierProperty());
   }

   public ComboBox<ProcurementOrderSupplier> getSupplier()
   {
      return supplier;
   }
}
