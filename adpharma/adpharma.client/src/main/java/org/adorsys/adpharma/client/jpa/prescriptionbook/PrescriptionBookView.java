package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import java.util.Calendar;

import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.prescriptionbook.PrescriptionBook;

public class PrescriptionBookView extends AbstractForm<PrescriptionBook>
{

   private TextField prescriptionNumber;

   private CalendarTextField prescriptionDate;

   private CalendarTextField recordingDate;

   @Inject
   private PrescriptionBookPrescriberForm prescriptionBookPrescriberForm;
   @Inject
   private PrescriptionBookPrescriberSelection prescriptionBookPrescriberSelection;

   @Inject
   private PrescriptionBookHospitalForm prescriptionBookHospitalForm;
   @Inject
   private PrescriptionBookHospitalSelection prescriptionBookHospitalSelection;

   @Inject
   private PrescriptionBookAgencyForm prescriptionBookAgencyForm;
   @Inject
   private PrescriptionBookAgencySelection prescriptionBookAgencySelection;

   @Inject
   private PrescriptionBookRecordingAgentForm prescriptionBookRecordingAgentForm;
   @Inject
   private PrescriptionBookRecordingAgentSelection prescriptionBookRecordingAgentSelection;

   @Inject
   private PrescriptionBookSalesOrderForm prescriptionBookSalesOrderForm;
   @Inject
   private PrescriptionBookSalesOrderSelection prescriptionBookSalesOrderSelection;

   @Inject
   @Bundle({ CrudKeys.class, PrescriptionBook.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      prescriptionNumber = viewBuilder.addTextField("PrescriptionBook_prescriptionNumber_description.title", "prescriptionNumber", resourceBundle,ViewModel.READ_ONLY);
      prescriptionDate = viewBuilder.addCalendarTextField("PrescriptionBook_prescriptionDate_description.title", "prescriptionDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      recordingDate = viewBuilder.addCalendarTextField("PrescriptionBook_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale,ViewModel.READ_ONLY);
      viewBuilder.addTitlePane("PrescriptionBook_prescriber_description.title", resourceBundle);
      viewBuilder.addSubForm("PrescriptionBook_prescriber_description.title", "prescriber", resourceBundle, prescriptionBookPrescriberForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("PrescriptionBook_prescriber_description.title", "prescriber", resourceBundle, prescriptionBookPrescriberSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("PrescriptionBook_hospital_description.title", resourceBundle);
      viewBuilder.addSubForm("PrescriptionBook_hospital_description.title", "hospital", resourceBundle, prescriptionBookHospitalForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("PrescriptionBook_hospital_description.title", "hospital", resourceBundle, prescriptionBookHospitalSelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("PrescriptionBook_agency_description.title", resourceBundle);
//      viewBuilder.addSubForm("PrescriptionBook_agency_description.title", "agency", resourceBundle, prescriptionBookAgencyForm, ViewModel.READ_ONLY);
//      viewBuilder.addSubForm("PrescriptionBook_agency_description.title", "agency", resourceBundle, prescriptionBookAgencySelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("PrescriptionBook_recordingAgent_description.title", resourceBundle);
//      viewBuilder.addSubForm("PrescriptionBook_recordingAgent_description.title", "recordingAgent", resourceBundle, prescriptionBookRecordingAgentForm, ViewModel.READ_ONLY);
//      viewBuilder.addSubForm("PrescriptionBook_recordingAgent_description.title", "recordingAgent", resourceBundle, prescriptionBookRecordingAgentSelection, ViewModel.READ_WRITE);
//      viewBuilder.addTitlePane("PrescriptionBook_salesOrder_description.title", resourceBundle);
//      viewBuilder.addSubForm("PrescriptionBook_salesOrder_description.title", "salesOrder", resourceBundle, prescriptionBookSalesOrderForm, ViewModel.READ_ONLY);
//      viewBuilder.addSubForm("PrescriptionBook_salesOrder_description.title", "salesOrder", resourceBundle, prescriptionBookSalesOrderSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      // no active validator
      // no active validator
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<PrescriptionBook>> validate(PrescriptionBook model)
   {
      Set<ConstraintViolation<PrescriptionBook>> violations = new HashSet<ConstraintViolation<PrescriptionBook>>();
      violations.addAll(toOneAggreggationFieldValidator.validate(prescriptionBookPrescriberSelection.getPrescriber(), model.getPrescriber(), PrescriptionBook.class, "prescriber", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(prescriptionBookHospitalSelection.getHospital(), model.getHospital(), PrescriptionBook.class, "hospital", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(prescriptionBookAgencySelection.getAgency(), model.getAgency(), PrescriptionBook.class, "agency", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(prescriptionBookRecordingAgentSelection.getRecordingAgent(), model.getRecordingAgent(), PrescriptionBook.class, "recordingAgent", resourceBundle));
      return violations;
   }

   public void bind(PrescriptionBook model)
   {
      prescriptionNumber.textProperty().bindBidirectional(model.prescriptionNumberProperty());
      prescriptionDate.calendarProperty().bindBidirectional(model.prescriptionDateProperty());
      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
      prescriptionBookPrescriberForm.bind(model);
      prescriptionBookPrescriberSelection.bind(model);
      prescriptionBookHospitalForm.bind(model);
      prescriptionBookHospitalSelection.bind(model);
      prescriptionBookAgencyForm.bind(model);
      prescriptionBookAgencySelection.bind(model);
      prescriptionBookRecordingAgentForm.bind(model);
      prescriptionBookRecordingAgentSelection.bind(model);
      prescriptionBookSalesOrderForm.bind(model);
      prescriptionBookSalesOrderSelection.bind(model);
   }

   public TextField getPrescriptionNumber()
   {
      return prescriptionNumber;
   }

   public CalendarTextField getPrescriptionDate()
   {
      return prescriptionDate;
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }

   public PrescriptionBookPrescriberForm getPrescriptionBookPrescriberForm()
   {
      return prescriptionBookPrescriberForm;
   }

   public PrescriptionBookPrescriberSelection getPrescriptionBookPrescriberSelection()
   {
      return prescriptionBookPrescriberSelection;
   }

   public PrescriptionBookHospitalForm getPrescriptionBookHospitalForm()
   {
      return prescriptionBookHospitalForm;
   }

   public PrescriptionBookHospitalSelection getPrescriptionBookHospitalSelection()
   {
      return prescriptionBookHospitalSelection;
   }

   public PrescriptionBookAgencyForm getPrescriptionBookAgencyForm()
   {
      return prescriptionBookAgencyForm;
   }

   public PrescriptionBookAgencySelection getPrescriptionBookAgencySelection()
   {
      return prescriptionBookAgencySelection;
   }

   public PrescriptionBookRecordingAgentForm getPrescriptionBookRecordingAgentForm()
   {
      return prescriptionBookRecordingAgentForm;
   }

   public PrescriptionBookRecordingAgentSelection getPrescriptionBookRecordingAgentSelection()
   {
      return prescriptionBookRecordingAgentSelection;
   }

   public PrescriptionBookSalesOrderForm getPrescriptionBookSalesOrderForm()
   {
      return prescriptionBookSalesOrderForm;
   }

   public PrescriptionBookSalesOrderSelection getPrescriptionBookSalesOrderSelection()
   {
      return prescriptionBookSalesOrderSelection;
   }
}
