package org.adorsys.adpharma.client.jpa.cashdrawer;

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

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class CashDrawerCashierSelection extends AbstractSelection<CashDrawer, Login>
{

   private ComboBox<CashDrawerCashier> cashier;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, CashDrawer.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      cashier = viewBuilder.addComboBox("CashDrawer_cashier_description.title", "cashier", resourceBundle, false);

      cashier.setCellFactory(new Callback<ListView<CashDrawerCashier>, ListCell<CashDrawerCashier>>()
      {
         @Override
         public ListCell<CashDrawerCashier> call(ListView<CashDrawerCashier> listView)
         {
            return new CashDrawerCashierListCell();
         }
      });
      cashier.setButtonCell(new CashDrawerCashierListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CashDrawer model)
   {
      cashier.valueProperty().bindBidirectional(model.cashierProperty());
   }

   public ComboBox<CashDrawerCashier> getCashier()
   {
      return cashier;
   }
}
