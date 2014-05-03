package org.adorsys.adpharma.client.jpa.documentarchive;

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

public class DocumentArchiveView extends AbstractForm<DocumentArchive>
{

   private TextField documentNumber;

   private TextField documentLocation;

   private ComboBox<DocumentType> documentType;

   private CalendarTextField modifiedDate;

   private CalendarTextField priodFrom;

   private CalendarTextField periodTo;

   @Inject
   private DocumentArchiveRecordingUserForm documentArchiveRecordingUserForm;
   @Inject
   private DocumentArchiveRecordingUserSelection documentArchiveRecordingUserSelection;

   @Inject
   @Bundle({ CrudKeys.class, DocumentArchive.class })
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
      documentNumber = viewBuilder.addTextField("DocumentArchive_documentNumber_description.title", "documentNumber", resourceBundle);
      documentLocation = viewBuilder.addTextField("DocumentArchive_documentLocation_description.title", "documentLocation", resourceBundle);
      documentType = viewBuilder.addComboBox("DocumentArchive_documentType_description.title", "documentType", resourceBundle, DocumentType.values());
      modifiedDate = viewBuilder.addCalendarTextField("DocumentArchive_modifiedDate_description.title", "modifiedDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      priodFrom = viewBuilder.addCalendarTextField("DocumentArchive_priodFrom_description.title", "priodFrom", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      periodTo = viewBuilder.addCalendarTextField("DocumentArchive_periodTo_description.title", "periodTo", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("DocumentArchive_recordingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("DocumentArchive_recordingUser_description.title", "recordingUser", resourceBundle, documentArchiveRecordingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("DocumentArchive_recordingUser_description.title", "recordingUser", resourceBundle, documentArchiveRecordingUserSelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(documentType, documentTypeConverter, documentTypeListCellFatory, documentTypeBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<DocumentArchive>> validate(DocumentArchive model)
   {
      Set<ConstraintViolation<DocumentArchive>> violations = new HashSet<ConstraintViolation<DocumentArchive>>();
      return violations;
   }

   public void bind(DocumentArchive model)
   {
      documentNumber.textProperty().bindBidirectional(model.documentNumberProperty());
      documentLocation.textProperty().bindBidirectional(model.documentLocationProperty());
      documentType.valueProperty().bindBidirectional(model.documentTypeProperty());
      modifiedDate.calendarProperty().bindBidirectional(model.modifiedDateProperty());
      priodFrom.calendarProperty().bindBidirectional(model.priodFromProperty());
      periodTo.calendarProperty().bindBidirectional(model.periodToProperty());
      documentArchiveRecordingUserForm.bind(model);
      documentArchiveRecordingUserSelection.bind(model);
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

   public DocumentArchiveRecordingUserForm getDocumentArchiveRecordingUserForm()
   {
      return documentArchiveRecordingUserForm;
   }

   public DocumentArchiveRecordingUserSelection getDocumentArchiveRecordingUserSelection()
   {
      return documentArchiveRecordingUserSelection;
   }
}
