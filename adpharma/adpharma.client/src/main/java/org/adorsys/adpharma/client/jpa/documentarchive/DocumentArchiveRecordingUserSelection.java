package org.adorsys.adpharma.client.jpa.documentarchive;

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

public class DocumentArchiveRecordingUserSelection extends AbstractSelection<DocumentArchive, Login>
{

   private ComboBox<DocumentArchiveRecordingUser> recordingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, DocumentArchive.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      recordingUser = viewBuilder.addComboBox("DocumentArchive_recordingUser_description.title", "recordingUser", resourceBundle, false);

      recordingUser.setCellFactory(new Callback<ListView<DocumentArchiveRecordingUser>, ListCell<DocumentArchiveRecordingUser>>()
      {
         @Override
         public ListCell<DocumentArchiveRecordingUser> call(ListView<DocumentArchiveRecordingUser> listView)
         {
            return new DocumentArchiveRecordingUserListCell();
         }
      });
      recordingUser.setButtonCell(new DocumentArchiveRecordingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(DocumentArchive model)
   {
      recordingUser.valueProperty().bindBidirectional(model.recordingUserProperty());
   }

   public ComboBox<DocumentArchiveRecordingUser> getRecordingUser()
   {
      return recordingUser;
   }
}
