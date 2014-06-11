package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SalesOrderCashDrawerSelection extends AbstractSelection<SalesOrder, CashDrawer>
{

   private ComboBox<SalesOrderCashDrawer> cashDrawer;

   @Inject
   @Bundle({ CrudKeys.class, CashDrawer.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      cashDrawer = viewBuilder.addComboBox("SalesOrder_cashDrawer_description.title", "cashDrawer", resourceBundle, false);

      cashDrawer.setCellFactory(new Callback<ListView<SalesOrderCashDrawer>, ListCell<SalesOrderCashDrawer>>()
      {
         @Override
         public ListCell<SalesOrderCashDrawer> call(ListView<SalesOrderCashDrawer> listView)
         {
            return new SalesOrderCashDrawerListCell();
         }
      });
      cashDrawer.setButtonCell(new SalesOrderCashDrawerListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
      cashDrawer.valueProperty().bindBidirectional(model.cashDrawerProperty());
   }

   public ComboBox<SalesOrderCashDrawer> getCashDrawer()
   {
      return cashDrawer;
   }
}