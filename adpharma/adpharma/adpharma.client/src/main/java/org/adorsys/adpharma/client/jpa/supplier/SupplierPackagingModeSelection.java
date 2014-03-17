package org.adorsys.adpharma.client.jpa.supplier;

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

import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public class SupplierPackagingModeSelection extends AbstractSelection<Supplier, PackagingMode>
{

   private ComboBox<PackagingMode> packagingMode;

   @Inject
   @Bundle({ CrudKeys.class, PackagingMode.class, Supplier.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      packagingMode = viewBuilder.addComboBox("Supplier_packagingMode_description.title", "packagingMode", resourceBundle, false);

      packagingMode.setCellFactory(new Callback<ListView<PackagingMode>, ListCell<PackagingMode>>()
      {
         @Override
         public ListCell<PackagingMode> call(ListView<PackagingMode> listView)
         {
            return new SupplierPackagingModeListCell();
         }
      });
      packagingMode.setButtonCell(new SupplierPackagingModeListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Supplier model)
   {
   }

   public ComboBox<PackagingMode> getPackagingMode()
   {
      return packagingMode;
   }
}
