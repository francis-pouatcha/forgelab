package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class InventoryItemRecordingUserSelection extends AbstractSelection<InventoryItem, Login>
{

   private ComboBox<InventoryItemRecordingUser> recordingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, InventoryItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      recordingUser = viewBuilder.addComboBox("InventoryItem_recordingUser_description.title", "recordingUser", resourceBundle, false);

      recordingUser.setCellFactory(new Callback<ListView<InventoryItemRecordingUser>, ListCell<InventoryItemRecordingUser>>()
      {
         @Override
         public ListCell<InventoryItemRecordingUser> call(ListView<InventoryItemRecordingUser> listView)
         {
            return new InventoryItemRecordingUserListCell();
         }
      });
      recordingUser.setButtonCell(new InventoryItemRecordingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(InventoryItem model)
   {
      recordingUser.valueProperty().bindBidirectional(model.recordingUserProperty());
   }

   public ComboBox<InventoryItemRecordingUser> getRecordingUser()
   {
      return recordingUser;
   }
}
