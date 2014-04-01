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

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

public class ProcurementOrderCreatingUserSelection extends AbstractSelection<ProcurementOrder, Login>
{

   private ComboBox<ProcurementOrderCreatingUser> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, ProcurementOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("ProcurementOrder_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<ProcurementOrderCreatingUser>, ListCell<ProcurementOrderCreatingUser>>()
      {
         @Override
         public ListCell<ProcurementOrderCreatingUser> call(ListView<ProcurementOrderCreatingUser> listView)
         {
            return new ProcurementOrderCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new ProcurementOrderCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrder model)
   {
      creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
   }

   public ComboBox<ProcurementOrderCreatingUser> getCreatingUser()
   {
      return creatingUser;
   }
}
