package org.adorsys.adpharma.client.jpa.prescriptionbook;

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

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

public class PrescriptionBookSalesOrderSelection extends AbstractSelection<PrescriptionBook, SalesOrder>
{

   private ComboBox<SalesOrder> salesOrder;

   @Inject
   @Bundle({ CrudKeys.class, SalesOrder.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      salesOrder = viewBuilder.addComboBox("PrescriptionBook_salesOrder_description.title", "salesOrder", resourceBundle, false);

      salesOrder.setCellFactory(new Callback<ListView<SalesOrder>, ListCell<SalesOrder>>()
      {
         @Override
         public ListCell<SalesOrder> call(ListView<SalesOrder> listView)
         {
            return new PrescriptionBookSalesOrderListCell();
         }
      });
      salesOrder.setButtonCell(new PrescriptionBookSalesOrderListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
   }

   public ComboBox<SalesOrder> getSalesOrder()
   {
      return salesOrder;
   }
}
