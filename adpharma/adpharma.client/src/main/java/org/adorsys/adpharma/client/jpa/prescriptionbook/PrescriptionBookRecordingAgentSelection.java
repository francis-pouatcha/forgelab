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

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

public class PrescriptionBookRecordingAgentSelection extends AbstractSelection<PrescriptionBook, Login>
{

   private ComboBox<PrescriptionBookRecordingAgent> recordingAgent;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      recordingAgent = viewBuilder.addComboBox("PrescriptionBook_recordingAgent_description.title", "recordingAgent", resourceBundle, false);

      recordingAgent.setCellFactory(new Callback<ListView<PrescriptionBookRecordingAgent>, ListCell<PrescriptionBookRecordingAgent>>()
      {
         @Override
         public ListCell<PrescriptionBookRecordingAgent> call(ListView<PrescriptionBookRecordingAgent> listView)
         {
            return new PrescriptionBookRecordingAgentListCell();
         }
      });
      recordingAgent.setButtonCell(new PrescriptionBookRecordingAgentListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(PrescriptionBook model)
   {
      recordingAgent.valueProperty().bindBidirectional(model.recordingAgentProperty());
   }

   public ComboBox<PrescriptionBookRecordingAgent> getRecordingAgent()
   {
      return recordingAgent;
   }
}
