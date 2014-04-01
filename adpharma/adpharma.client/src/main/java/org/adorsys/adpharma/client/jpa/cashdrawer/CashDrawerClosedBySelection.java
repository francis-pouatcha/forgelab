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

public class CashDrawerClosedBySelection extends AbstractSelection<CashDrawer, Login>
{

   private ComboBox<CashDrawerClosedBy> closedBy;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, CashDrawer.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      closedBy = viewBuilder.addComboBox("CashDrawer_closedBy_description.title", "closedBy", resourceBundle, false);

      closedBy.setCellFactory(new Callback<ListView<CashDrawerClosedBy>, ListCell<CashDrawerClosedBy>>()
      {
         @Override
         public ListCell<CashDrawerClosedBy> call(ListView<CashDrawerClosedBy> listView)
         {
            return new CashDrawerClosedByListCell();
         }
      });
      closedBy.setButtonCell(new CashDrawerClosedByListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CashDrawer model)
   {
      closedBy.valueProperty().bindBidirectional(model.closedByProperty());
   }

   public ComboBox<CashDrawerClosedBy> getClosedBy()
   {
      return closedBy;
   }
}
