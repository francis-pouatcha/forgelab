package org.adorsys.adpharma.client.jpa.inventoryitem;

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
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;

public class InventoryItemRecordingUserSelection extends AbstractSelection<InventoryItem, Login>
{

   private ComboBox<Login> recordingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, InventoryItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      recordingUser = viewBuilder.addComboBox("InventoryItem_recordingUser_description.title", "recordingUser", resourceBundle, false);

      recordingUser.setCellFactory(new Callback<ListView<Login>, ListCell<Login>>()
      {
         @Override
         public ListCell<Login> call(ListView<Login> listView)
         {
            return new InventoryItemRecordingUserListCell();
         }
      });
      recordingUser.setButtonCell(new InventoryItemRecordingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(InventoryItem model)
   {
   }

   public ComboBox<Login> getRecordingUser()
   {
      return recordingUser;
   }
}
