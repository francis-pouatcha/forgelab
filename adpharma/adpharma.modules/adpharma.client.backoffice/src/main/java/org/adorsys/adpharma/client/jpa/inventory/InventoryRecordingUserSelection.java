package org.adorsys.adpharma.client.jpa.inventory;

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

public class InventoryRecordingUserSelection extends AbstractSelection<Inventory, Login>
{

   private ComboBox<InventoryRecordingUser> recordingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, Inventory.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      recordingUser = viewBuilder.addComboBox("Inventory_recordingUser_description.title", "recordingUser", resourceBundle, false);

      recordingUser.setCellFactory(new Callback<ListView<InventoryRecordingUser>, ListCell<InventoryRecordingUser>>()
      {
         @Override
         public ListCell<InventoryRecordingUser> call(ListView<InventoryRecordingUser> listView)
         {
            return new InventoryRecordingUserListCell();
         }
      });
      recordingUser.setButtonCell(new InventoryRecordingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Inventory model)
   {
      recordingUser.valueProperty().bindBidirectional(model.recordingUserProperty());
   }

   public ComboBox<InventoryRecordingUser> getRecordingUser()
   {
      return recordingUser;
   }
}