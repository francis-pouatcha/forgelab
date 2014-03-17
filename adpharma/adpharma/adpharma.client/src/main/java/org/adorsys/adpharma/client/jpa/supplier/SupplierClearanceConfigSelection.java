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

import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;

public class SupplierClearanceConfigSelection extends AbstractSelection<Supplier, ClearanceConfig>
{

   private ComboBox<ClearanceConfig> clearanceConfig;

   @Inject
   @Bundle({ CrudKeys.class, ClearanceConfig.class, Supplier.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      clearanceConfig = viewBuilder.addComboBox("Supplier_clearanceConfig_description.title", "clearanceConfig", resourceBundle, false);

      clearanceConfig.setCellFactory(new Callback<ListView<ClearanceConfig>, ListCell<ClearanceConfig>>()
      {
         @Override
         public ListCell<ClearanceConfig> call(ListView<ClearanceConfig> listView)
         {
            return new SupplierClearanceConfigListCell();
         }
      });
      clearanceConfig.setButtonCell(new SupplierClearanceConfigListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Supplier model)
   {
   }

   public ComboBox<ClearanceConfig> getClearanceConfig()
   {
      return clearanceConfig;
   }
}
