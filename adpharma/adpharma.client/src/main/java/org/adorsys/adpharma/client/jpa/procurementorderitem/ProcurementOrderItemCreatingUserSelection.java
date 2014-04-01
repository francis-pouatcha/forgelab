package org.adorsys.adpharma.client.jpa.procurementorderitem;

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
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;

public class ProcurementOrderItemCreatingUserSelection extends AbstractSelection<ProcurementOrderItem, Login>
{

   private ComboBox<ProcurementOrderItemCreatingUser> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, ProcurementOrderItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("ProcurementOrderItem_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<ProcurementOrderItemCreatingUser>, ListCell<ProcurementOrderItemCreatingUser>>()
      {
         @Override
         public ListCell<ProcurementOrderItemCreatingUser> call(ListView<ProcurementOrderItemCreatingUser> listView)
         {
            return new ProcurementOrderItemCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new ProcurementOrderItemCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProcurementOrderItem model)
   {
      creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
   }

   public ComboBox<ProcurementOrderItemCreatingUser> getCreatingUser()
   {
      return creatingUser;
   }
}
