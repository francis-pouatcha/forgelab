package org.adorsys.adpharma.client.jpa.documentstore;

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
import org.adorsys.adpharma.client.jpa.documentstore.DocumentStore;

public class DocumentStoreRecordingUserSelection extends AbstractSelection<DocumentStore, Login>
{

   private ComboBox<DocumentStoreRecordingUser> recordingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, DocumentStore.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      recordingUser = viewBuilder.addComboBox("DocumentStore_recordingUser_description.title", "recordingUser", resourceBundle, false);

      recordingUser.setCellFactory(new Callback<ListView<DocumentStoreRecordingUser>, ListCell<DocumentStoreRecordingUser>>()
      {
         @Override
         public ListCell<DocumentStoreRecordingUser> call(ListView<DocumentStoreRecordingUser> listView)
         {
            return new DocumentStoreRecordingUserListCell();
         }
      });
      recordingUser.setButtonCell(new DocumentStoreRecordingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(DocumentStore model)
   {
      recordingUser.valueProperty().bindBidirectional(model.recordingUserProperty());
   }

   public ComboBox<DocumentStoreRecordingUser> getRecordingUser()
   {
      return recordingUser;
   }
}
