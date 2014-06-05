package org.adorsys.adpharma.client.jpa.documentstore;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.documenttype.DocumentType;
import org.adorsys.adpharma.client.jpa.documenttype.DocumentTypeConverter;
import org.adorsys.adpharma.client.jpa.documenttype.DocumentTypeListCellFatory;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DocumentStoreView extends AbstractForm<DocumentStore>
{

   private TextField documentNumber;

   private TextField documentLocation;

   private ComboBox<DocumentType> documentType;

   private CalendarTextField modifiedDate;

   private CalendarTextField priodFrom;

   private CalendarTextField periodTo;

   @Inject
   private DocumentStoreRecordingUserForm documentStoreRecordingUserForm;
   @Inject
   private DocumentStoreRecordingUserSelection documentStoreRecordingUserSelection;

   @Inject
   @Bundle({ CrudKeys.class, DocumentStore.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentType.class)
   private ResourceBundle documentTypeBundle;

   @Inject
   private DocumentTypeConverter documentTypeConverter;

   @Inject
   private DocumentTypeListCellFatory documentTypeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      documentNumber = viewBuilder.addTextField("DocumentStore_documentNumber_description.title", "documentNumber", resourceBundle);
      documentLocation = viewBuilder.addTextField("DocumentStore_documentLocation_description.title", "documentLocation", resourceBundle);
      documentType = viewBuilder.addComboBox("DocumentStore_documentType_description.title", "documentType", resourceBundle, DocumentType.values());
      modifiedDate = viewBuilder.addCalendarTextField("DocumentStore_modifiedDate_description.title", "modifiedDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      priodFrom = viewBuilder.addCalendarTextField("DocumentStore_priodFrom_description.title", "priodFrom", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      periodTo = viewBuilder.addCalendarTextField("DocumentStore_periodTo_description.title", "periodTo", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("DocumentStore_recordingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("DocumentStore_recordingUser_description.title", "recordingUser", resourceBundle, documentStoreRecordingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("DocumentStore_recordingUser_description.title", "recordingUser", resourceBundle, documentStoreRecordingUserSelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(documentType, documentTypeConverter, documentTypeListCellFatory, documentTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<DocumentStore>> validate(DocumentStore model)
   {
      Set<ConstraintViolation<DocumentStore>> violations = new HashSet<ConstraintViolation<DocumentStore>>();
      return violations;
   }

   public void bind(DocumentStore model)
   {
      documentNumber.textProperty().bindBidirectional(model.documentNumberProperty());
      documentLocation.textProperty().bindBidirectional(model.documentLocationProperty());
      documentType.valueProperty().bindBidirectional(model.documentTypeProperty());
      modifiedDate.calendarProperty().bindBidirectional(model.modifiedDateProperty());
      priodFrom.calendarProperty().bindBidirectional(model.priodFromProperty());
      periodTo.calendarProperty().bindBidirectional(model.periodToProperty());
      documentStoreRecordingUserForm.bind(model);
      documentStoreRecordingUserSelection.bind(model);
   }

   public TextField getDocumentNumber()
   {
      return documentNumber;
   }

   public TextField getDocumentLocation()
   {
      return documentLocation;
   }

   public ComboBox<DocumentType> getDocumentType()
   {
      return documentType;
   }

   public CalendarTextField getModifiedDate()
   {
      return modifiedDate;
   }

   public CalendarTextField getPriodFrom()
   {
      return priodFrom;
   }

   public CalendarTextField getPeriodTo()
   {
      return periodTo;
   }

   public DocumentStoreRecordingUserForm getDocumentStoreRecordingUserForm()
   {
      return documentStoreRecordingUserForm;
   }

   public DocumentStoreRecordingUserSelection getDocumentStoreRecordingUserSelection()
   {
      return documentStoreRecordingUserSelection;
   }
}
